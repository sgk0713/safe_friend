package com.seoul_app_contest.safe_friend.protector_main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.RequestModel;
import com.seoul_app_contest.safe_friend.UserModel;
import com.seoul_app_contest.safe_friend.adapter.ProtectorRecyclerViewAdapter;
import com.seoul_app_contest.safe_friend.login.LoginActivity;
import com.seoul_app_contest.safe_friend.main.MainActivity;
import com.seoul_app_contest.safe_friend.profile.ProfileActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProtectorMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ProtectorMainContract.View {
    @BindView(R.id.protector_location_tv)
    TextView protectorLocationTv;
    @BindView(R.id.protector_num_tv)
    TextView protectorNumTv;
    @BindView(R.id.protector_rv)
    RecyclerView protectorRv;

    @BindView(R.id.protector_main_tb)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.protector_main_nv)
    NavigationView navigationView;
    @BindView(R.id.protector_main_drawer)
    DrawerLayout drawerLayout;

    private ArrayList<RequestModel> arrayList = new ArrayList<>();
    TextView navNameTv;
    TextView navEmailTv;
    private ProtectorMainContract.Presenter presenter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    private String uid;
    private String chargeStreet;//지킴이 할당구역
    String TAG = "WATING_DEBUG";
    ProtectorRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector_main);
        init();
    }

    private void init() {
        presenter = new ProtectorMainPresenter(this);
        ButterKnife.bind(this);
//        arrayList.add(new RequestModel("11:30", "gggg", "dd"));
//        arrayList.add(new RequestModel("11:30", "gggg", "dd"));
//        arrayList.add(new RequestModel("11:30", "gggg", "dd"));

        uid = auth.getCurrentUser().getUid();
        Log.d(TAG, "uid:" + uid);
        db.collection("PROTECTORS").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    chargeStreet = task.getResult().getString("address");
                    Log.d(TAG, "chargeStreet:" + chargeStreet);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

        adapter = new ProtectorRecyclerViewAdapter(this, arrayList);
        protectorRv.setLayoutManager(new LinearLayoutManager(this));
        protectorRv.setAdapter(adapter);

        //요청이 올때 관할구역이면 생성한다
        db.collection("WAITING_LIST").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    for(DocumentSnapshot snapshot:queryDocumentSnapshots.getDocuments()) {
                        if(chargeStreet.equals(snapshot.getString("street"))) {
                            arrayList.add(snapshot.toObject(RequestModel.class));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        setSupportActionBar(toolbar);
        navNameTv = navigationView.getHeaderView(0).findViewById(R.id.nav_name_tv);
        navEmailTv = navigationView.getHeaderView(0).findViewById(R.id.nav_email_tv);
        presenter.setUserData();
//        navNameTv.setText(new UserModel().getU);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).findViewById(R.id.nav_logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signOut();

            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.nav_profile_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectProfileActivity();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void setNavName(String name) {
        navNameTv.setText(name);
    }

    @Override
    public void setNavEmail(String email) {
        navEmailTv.setText(email);
    }

    @Override
    public void redirectLoginActivity() {
        Intent intent = new Intent(ProtectorMainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void redirectProfileActivity() {
        Intent intent = new Intent(ProtectorMainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
