package com.seoul_app_contest.safe_friend.waitingncomfirm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.map.MapsActivity;


public class ConfirmationFragment extends Fragment {

    public ConfirmationFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation,container,false);
        setImageView((ImageView) view.findViewById(R.id.followerAProfile));
        setImageView((ImageView) view.findViewById(R.id.followerBProfile));
        view.findViewById(R.id.confirmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra("TYPE", "follower");
                startActivity(intent);
                ((Activity) view.getContext()).finish();

            }
        });
        return view;
    }



    private void setImageView(ImageView imageView){
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        imageView.setClipToOutline(true);
    }
}
