package com.seoul_app_contest.safe_friend.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperLastItem extends SQLiteOpenHelper {

    private int LIMIT_NUMBER = 5;
    String tableName = "lastitem";
    public DBHelperLastItem(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table if not exists " + tableName + "(order_no INTEGER, stop_no TEXT, stop_nm TEXT, xcode REAL, ycode REAL);";
        db.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(int num, String stop_no, String stop_nm, float xcode, float ycode){
        SQLiteDatabase db = getWritableDatabase();
        String qry = "insert into " + tableName + "(order_no, stop_no, stop_nm, xcode, ycode) values(?,?,?,?,?)";
        Object[] params = {num, stop_no, stop_nm, xcode, ycode};
        db.execSQL(qry, params);
        db.close();
    }

    public void delete(int num){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM lastitem WHERE order_no = "+ num +";");
        db.close();
    }

    public int getTotal(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lastitem;", null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public String getStopNo(int order_no){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lastitem WHERE order_no = "+order_no+";", null);
        String str = "";
        while(cursor.moveToNext()){
            str = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return str;
    }
    public String getStopNm(int order_no){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lastitem WHERE order_no = "+order_no+";", null);
        String str = "";
        while(cursor.moveToNext()){
            str = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return str;
    }
    public String getXcode(int order_no){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lastitem WHERE order_no = "+order_no+";", null);
        String str = "";
        while(cursor.moveToNext()){
            str = String.valueOf(cursor.getFloat(3));
        }
        cursor.close();
        db.close();
        return str;
    }

    public String getYcode(int order_no){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lastitem WHERE order_no = "+order_no+";", null);
        String str = "";
        while(cursor.moveToNext()){
            str = String.valueOf(cursor.getFloat(4));
        }
        cursor.close();
        db.close();
        return str;
    }


    public void sortAsLRU(String stop_no, String stop_nm, float xcode, float ycode){
        String qry = "";
        SQLiteDatabase db = getWritableDatabase();

        qry = "SELECT * FROM lastitem WHERE stop_no = '" + stop_no + "' and stop_nm = '"+stop_nm+"';";
        Cursor cursor = db.rawQuery(qry, null);
Log.d("TESTEST", "qry : " + qry + "cursor.getCount1 : " + cursor.getCount());


        if(cursor.getCount() == 0){//디비에 없다면
            cursor = db.rawQuery("SELECT * FROM lastitem", null);
Log.d("TESTEST", "cursor.getCount2 : " + cursor.getCount());
            while(cursor.moveToNext()){
                qry = "UPDATE lastitem SET order_no =" + (cursor.getInt(0)+1)
                        + " WHERE order_no = " + cursor.getInt(0);
                db.execSQL(qry);
                qry = "";
            }
            qry = "INSERT INTO lastitem VALUES("+1+", '"+stop_no+"', '"+stop_nm+"',"+xcode+" ,"+ycode+" );";
Log.d("TESTEST", "qry==" + qry);
            db.execSQL(qry);
        }

        else{//기존에 존재하는것이라면
            cursor = db.rawQuery("SELECT * FROM lastitem WHERE stop_no = '" + stop_no + "' and stop_nm = '"+stop_nm+"';", null);
Log.d("TESTEST", "cursor.getCount3 : " + cursor.getCount());
            int orderNum = 0;
            while(cursor.moveToNext()){
                orderNum = cursor.getInt(0);
            }
            int i = orderNum-1;
            while(i > 0){
                cursor = db.rawQuery("SELECT * FROM lastitem WHERE order_no = "+ i +";", null);
                while(cursor.moveToNext()){
                    qry = "UPDATE lastitem SET order_no = " + (cursor.getInt(0)+1)+ " WHERE order_no = " + cursor.getInt(0);
Log.d("TESTEST", "WHILE_QUERY : " + qry);
                    db.execSQL(qry);
                    qry = "";
                }
                i--;
            }
            qry = "UPDATE lastitem SET order_no = 1 WHERE stop_no = '" + stop_no + "' and stop_nm = '"+stop_nm+"';";
Log.d("TESTEST", "QUERY : " + qry);
            db.execSQL(qry);
        }

        db.execSQL("DELETE FROM lastitem WHERE order_no > "+ LIMIT_NUMBER +";");
        cursor.close();
        db.close();
    }
}
