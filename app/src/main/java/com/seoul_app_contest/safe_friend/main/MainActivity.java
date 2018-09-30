package com.seoul_app_contest.safe_friend.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.seoul_app_contest.safe_friend.login.LoginActivity;
import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.SearchPlaceActivity;
import com.seoul_app_contest.safe_friend.profile.ProfileActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    MainContract.Presenter presenter;

    @BindView(R.id.main_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TextView navNameTv;
    private TextView navEmailTv;
    ImageView navProfileIv;
    @OnClick(R.id.main_call_btn)
    void callBtn() {
        redirectCall();
    }

    @OnClick(R.id.main_app_btn)
    void appBtn() {
        redirectSearchPlaceActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.showExplanationDialog();

        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
         navProfileIv = navigationView.getHeaderView(0).findViewById(R.id.nav_profile_iv);
        navNameTv = navigationView.getHeaderView(0).findViewById(R.id.nav_name_tv);
        navEmailTv = navigationView.getHeaderView(0).findViewById(R.id.nav_email_tv);
        presenter.setUserData();
        navigationView.getHeaderView(0).findViewById(R.id.nav_logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.nav_profile_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerOpen(Gravity.START)) {
                    drawerLayout.closeDrawer(Gravity.START);
                }
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);


            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("접근 권한이 필요해요")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.CALL_PHONE)
                .check();
    }

    @Override
    public void redirectCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01024347280"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @Override
    public void redirectSearchPlaceActivity() {
        Intent intent = new Intent(this, SearchPlaceActivity.class);
        startActivity(intent);
    }

    @Override
    public void showExplanationDialog() {
        final SpannableStringBuilder sp = new SpannableStringBuilder("안심귀가서비스란,");
        sp.setSpan(new ForegroundColorSpan(getColor(R.color.mainColor)), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new RelativeSizeSpan((float) 1.2), 0,7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_explanation, null, false);
        ((TextView) dialogView.findViewById(R.id.dialog_explanation_service_tv)).setText(sp);
        Button explanationConfirmBtn = dialogView.findViewById(R.id.dialog_explanation_confirm_btn);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.show();
        explanationConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
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
    public void setNavProfile(String url) {
        Glide.with(navigationView).load(url).apply(new RequestOptions().circleCrop()).into(navProfileIv);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout_menu: {
                presenter.signOut();
                break;
            }
        }

        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.closeDrawer(Gravity.END);
        }
        return false;
    }

}
