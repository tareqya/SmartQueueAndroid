package com.samrtq.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.samrtq.R;
import com.samrtq.callback.AppointmentCallBack;
import com.samrtq.callback.DoctorCallBack;
import com.samrtq.controls.AppointmentController;
import com.samrtq.controls.AuthControl;
import com.samrtq.controls.DoctorController;
import com.samrtq.entities.Appointment;
import com.samrtq.entities.Doctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAppointmentFragment extends Fragment {

    private Activity context;
    private Spinner fAddAppointment_SP_doctor;
    private Button fAddAppointment_BTN_pickTime;
    private TextView fAddAppointment_TV_pickTime;
    private Button fAddAppointment_BTN_pickDate;
    private TextView fAddAppointment_TV_pickDate;
    private Button fAddAppointment_BTN_book;
    private FragmentManager fragmentManager;
    private DoctorController doctorController;
    private AppointmentController appointmentController;
    private CircularProgressIndicator fAddAppointment_PB_loading;



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
        doctorController = new DoctorController();
        doctorController.setDoctorCallBack(new DoctorCallBack() {
            @Override
            public void onDoctorsFetchComplete(ArrayList<Doctor> doctors) {
                ArrayAdapter<Doctor> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, doctors);
                fAddAppointment_SP_doctor.setAdapter(adapter);
            }
        });
        doctorController.fetchAllDoctors();

        appointmentController = new AppointmentController();
        appointmentController.setAppointmentCallBack(new AppointmentCallBack() {
            @Override
            public void onAddAppointmentComplete(Task<Void> task) {
                fAddAppointment_PB_loading.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Toast.makeText(context, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                    fAddAppointment_TV_pickDate.setText("");
                    fAddAppointment_TV_pickTime.setText("");
                }else{
                    Toast.makeText(context, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFetchAppointmentsComplete(ArrayList<Appointment> appointments) {

            }

            @Override
            public void onCancelAppointmentComplete(Task<Void> task) {

            }

            @Override
            public void onUpdateAppointmentComplete(Task<Void> task) {

            }
        });

        fAddAppointment_BTN_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDatePicker();
            }
        });

        fAddAppointment_BTN_pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleTimePicker();
            }
        });

        fAddAppointment_BTN_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!checkInputs()) {
                        Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    bookAppointment();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private boolean checkInputs() {
        if(fAddAppointment_TV_pickDate.getText().toString().length() == 0){
            return false;
        }
        if(fAddAppointment_TV_pickTime.getText().toString().length() == 0 ){
            return false;
        }
        return true;
    }

    private void bookAppointment() throws ParseException {
        AuthControl authControl = new AuthControl();
        String dateTimeString = fAddAppointment_TV_pickDate.getText().toString() + " " + fAddAppointment_TV_pickTime.getText().toString(); // Replace this with your date and time string
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        Date dateTime = sdf.parse(dateTimeString);
        Doctor doctor = (Doctor) fAddAppointment_SP_doctor.getSelectedItem();

        String uid = authControl.getCurrentUser().getUid();
        Appointment appointment = new Appointment()
                .setDate(dateTime)
                .setDoctor(doctor)
                .setClientId(uid);

        appointmentController.addAppointment(appointment);
        fAddAppointment_PB_loading.setVisibility(View.VISIBLE);
    }

    private void toggleTimePicker() {
        MaterialTimePicker picker =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select Appointment time")
                        .build();

        picker.show(fragmentManager, "timePicker");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = picker.getHour() + ":" + picker.getMinute();
                fAddAppointment_TV_pickTime.setText(time);
            }
        });
    }

    private void toggleDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.show(fragmentManager, "datePicker");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Date date = new Date(selection);
                // Create a SimpleDateFormat object with the desired format
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                // Use the format() method to convert Date to String
                String formattedDate = sdf.format(date);
                fAddAppointment_TV_pickDate.setText(formattedDate);
            }
        });
    }

    private void findViews(View root) {
        fAddAppointment_BTN_pickDate = root.findViewById(R.id.fAddAppointment_BTN_pickDate);
        fAddAppointment_TV_pickDate = root.findViewById(R.id.fAddAppointment_TV_pickDate);
        fAddAppointment_SP_doctor = root.findViewById(R.id.fAddAppointment_SP_doctor);
        fAddAppointment_BTN_pickTime = root.findViewById(R.id.fAddAppointment_BTN_pickTime);
        fAddAppointment_TV_pickTime = root.findViewById(R.id.fAddAppointment_TV_pickTime);
        fAddAppointment_BTN_book = root.findViewById(R.id.fAddAppointment_BTN_book);
        fAddAppointment_PB_loading = root.findViewById(R.id.fAddAppointment_PB_loading);

    }
}