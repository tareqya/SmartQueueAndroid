package com.samrtq.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.samrtq.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAppointmentFragment extends Fragment {

    private Activity context;
    private Button fAddAppointment_BTN_pickDate;
    private TextView fAddAppointment_TV_pickDate;

    private FragmentManager fragmentManager;
    private MaterialDatePicker<Long> datePicker;

    public AddAppointmentFragment(Activity context, FragmentManager fragmentManager) {
        // Required empty public constructor
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        findViews(root);
        initVars();
        return root;
    }

    private void initVars() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        datePicker = builder.build();
        fAddAppointment_BTN_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePicker.show(fragmentManager, "tag");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        Date date = new Date(selection);
                        fAddAppointment_TV_pickDate.setText(date.toString());
                        // Create a SimpleDateFormat object with the desired format
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                        // Use the format() method to convert Date to String
                        String formattedDate = sdf.format(date);
                        fAddAppointment_TV_pickDate.setText(formattedDate);
                    }
                });
            }
        });
    }

    private void findViews(View root) {
        fAddAppointment_BTN_pickDate = root.findViewById(R.id.fAddAppointment_BTN_pickDate);
        fAddAppointment_TV_pickDate = root.findViewById(R.id.fAddAppointment_TV_pickDate);
    }
}