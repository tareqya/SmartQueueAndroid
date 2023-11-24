package com.samrtq.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samrtq.R;
import com.samrtq.controls.AuthControl;
import com.samrtq.controls.UserControl;
import com.samrtq.entities.User;


public class HomeFragment extends Fragment {

    private Activity context;
    private User user;

    private TextView fHome_TV_name;
    public HomeFragment(Activity context) {
        // Required empty public constructor
        this.context = context;
    }

    public void setUser(User user) {
        this.user = user;
        fHome_TV_name.setText("Hello " + user.getFirstName() + ", ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(root);
        initVars();
        return root;
    }

    private void initVars() {

    }

    private void findViews(View root) {
        fHome_TV_name = root.findViewById(R.id.fHome_TV_name);
    }
}