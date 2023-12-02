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

import com.samrtq.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private Activity context;
    private CircleImageView profile_CIV_image;
    private TextView profile_TV_name;
    private TextView profile_TV_email;

    private CardView profile_CV_editDetails;


    public ProfileFragment(Activity context) {
        // Required empty public constructor
        this.context = context;
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
                startActivity(intent);
            }
        });
    }

    private void findViews(View view) {
        profile_CV_editDetails = view.findViewById(R.id.profile_CV_editDetails);
    }


}