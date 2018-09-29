package com.seoul_app_contest.safe_friend.postcode;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.register.RegisterActivity;
import com.seoul_app_contest.safe_friend.adapter.PostcodeRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.dto.PostDto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostcodeActivity extends AppCompatActivity implements PostcodeRecyclerViewAdapter.ReturnDataListener {
    private String apiUrl = "http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdService/retrieveNewAdressAreaCdService/getNewAddressListAreaCd?ServiceKey=zx0GlL2RVyBHKsGY1QUduUR0txiL4ZEByp%2BZ5M1v%2FUbM4XxEV%2Brv%2FoFP5iKMpzGdeKvGpXg1wKL7E%2ByWoLdjEw%3D%3D&searchSe=road&srchwrd=";
    Asynck asynck;
    @BindView(R.id.postcode_address_edt)
    EditText postcodeAddressEdt;
    @BindView(R.id.postcode_rv)
    RecyclerView postcodeRv;

    @OnClick(R.id.postcode_search_btn)
    void search() {
        asynck = new Asynck();
        asynck.execute();


    }

    PostcodeRecyclerViewAdapter adapter;
    ArrayList<PostDto> postDtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcode);
        ButterKnife.bind(this);
        adapter = new PostcodeRecyclerViewAdapter(this, postDtos, this);
        postcodeRv.setLayoutManager(new LinearLayoutManager(this));
        postcodeRv.setAdapter(adapter);



    }

    @Override
    public void returnData(PostDto postDto) {
        Log.d("BEOM123", "click : " + postDto.getZipNo());
        Intent intent = new Intent(PostcodeActivity.this, RegisterActivity.class);
        intent.putExtra("post", postDto);
        setResult(RESULT_OK, intent);
        finish();
    }

    class Asynck extends AsyncTask<String, Void, ArrayList<PostDto>> {

        @Override
        protected ArrayList<PostDto> doInBackground(String... strings) {
//            apiUrl += postcodeAddressEdt.getText().toString();
//            HttpURLConnection connection = null;
//
//            URL url = null;
//            try {
//                url = new URL(apiUrl + postcodeAddressEdt.getText().toString());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            try {
//                connection = (HttpURLConnection) url.openConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactoty.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = dBuilder.parse(apiUrl + postcodeAddressEdt.getText().toString());
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // root tag
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("newAddressListAreaCd");
            //System.out.println("파싱할 리스트 수 : "+ nList.getLength());

            postDtos.clear();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Log.d("BEOM123", getTagValue("zipNo", eElement));
                    Log.d("BEOM123", getTagValue("lnmAdres", eElement));
                    Log.d("BEOM123", getTagValue("rnAdres", eElement));
                    postDtos.add(new PostDto(getTagValue("zipNo", eElement), getTagValue("lnmAdres", eElement), getTagValue("rnAdres", eElement)));
                }  // for end
            }  // if end
            handler.sendMessage(handler.obtainMessage());
            return postDtos;
        }

        @Override
        protected void onPostExecute(ArrayList<PostDto> postDtos) {

        }
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };
}
