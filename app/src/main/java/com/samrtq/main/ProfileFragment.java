package com.samrtq.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.samrtq.R;
import com.samrtq.auth.LoginActivity;
import com.samrtq.controls.AuthControl;
import com.samrtq.entities.User;
import com.samrtq.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private Activity context;
    private CircleImageView profile_CIV_image;
    private TextView profile_TV_name;
    private TextView profile_TV_email;
    private User user;
    private CardView profile_CV_editDetails;
    private CardView profile_CV_logout;

    public ProfileFragment(Activity context) {
        // Required empty public constructor
        this.context = context;
    }

    public void setUser(User user){
        this.user = user;
        displayData();
    }

    private void displayData() {
        profile_TV_name.setText(user.getFullName());
        profile_TV_email.setText(user.getEmail());
        if(user.getImageUrl() != null){
            Glide.with(context).load(user.getImageUrl()).into(profile_CIV_image);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        profile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileEditActivity.class);
                intent.putExtra(Constants.USER, user);
                startActivity(intent);
            }
        });

        profile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthControl authControl = new AuthControl();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                authControl.signOut();
                context.finish();
            }
        });
    }

    private void findViews(View view) {
        profile_CV_editDetails = view.findViewById(R.id.profile_CV_editDetails);
        profile_TV_email = view.findViewById(R.id.profile_TV_email);
        profile_TV_name = view.findViewById(R.id.profile_TV_name);
        profile_CIV_image = view.findViewById(R.id.profile_CIV_image);
        profile_CV_logout = view.findViewById(R.id.profile_CV_logout);
    }


}