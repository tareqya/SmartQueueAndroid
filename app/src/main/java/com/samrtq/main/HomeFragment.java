package com.samrtq.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.samrtq.R;
import com.samrtq.adapter.AppointmentAdapter;
import com.samrtq.callback.AppointmentCallBack;
import com.samrtq.callback.OnClickAppointment;
import com.samrtq.controls.AppointmentController;
import com.samrtq.controls.AuthControl;
import com.samrtq.entities.Appointment;
import com.samrtq.entities.User;
import com.samrtq.utils.Constants;

import java.util.ArrayList;

import java.util.Collections;


public class HomeFragment extends Fragment {
    private Activity context;
    private User user;
    private AppointmentController appointmentController;
    private AuthControl authControl;
    private ArrayList<Appointment> appointments;
    private TextView fHome_TV_name;
    private RecyclerView fHome_RV_appointments;

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
        appointmentController = new AppointmentController();
        authControl = new AuthControl();
        appointmentController.setAppointmentCallBack(new AppointmentCallBack() {
            @Override
            public void onAddAppointmentComplete(Task<Void> task) {

            }

            @Override
            public void onFetchUserAppointmentsComplete(ArrayList<Appointment> userAppointments) {
                appointments = userAppointments;
                Collections.sort(appointments);

                AppointmentAdapter appointmentAdapter = new AppointmentAdapter(context, appointments);
                appointmentAdapter.setOnClickAppointment(new OnClickAppointment() {
                    @Override
                    public void onClick(Appointment appointment, int position) {
                        Intent intent = new Intent(context, AppointmentManagementActivity.class);
                        intent.putExtra(Constants.SELECTED_APPOINTMENT, appointment);
                        startActivity(intent);
                    }
                });
                fHome_RV_appointments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                fHome_RV_appointments.setHasFixedSize(true);
                fHome_RV_appointments.setItemAnimator(new DefaultItemAnimator());
                fHome_RV_appointments.setAdapter(appointmentAdapter);
            }
        });

        String uid = authControl.getCurrentUser().getUid();
        appointmentController.fetchUserAppointment(uid);
    }

    private void findViews(View root) {
        fHome_TV_name = root.findViewById(R.id.fHome_TV_name);
        fHome_RV_appointments = root.findViewById(R.id.fHome_RV_appointments);
    }
}