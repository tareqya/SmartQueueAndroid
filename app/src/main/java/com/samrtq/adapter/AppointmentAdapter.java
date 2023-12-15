package com.samrtq.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.samrtq.App;
import com.samrtq.R;
import com.samrtq.callback.OnClickAppointment;
import com.samrtq.entities.Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity activity;
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private OnClickAppointment onClickAppointment;

    public AppointmentAdapter(Activity activity, ArrayList<Appointment> appointments){
        this.appointments = appointments;
        this.activity = activity;
    }

    public void setOnClickAppointment(OnClickAppointment onClickAppointment){
        this.onClickAppointment = onClickAppointment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AppointmentViewHolder appointmentViewHolder = (AppointmentViewHolder) holder;
        Appointment appointment = getItem(position);

        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = desiredDateFormat.format(appointment.getDate());
        appointmentViewHolder.appointment_TV_appointmentTime.setText(formattedDate);

        appointmentViewHolder.appointment_TV_doctorName.setText(appointment.getDoctor().getName());
        appointmentViewHolder.appointment_TV_doctorSpecialist.setText(appointment.getDoctor().getSpecialist());
        Glide.with(activity).load(appointment.getDoctor().getImageUrl())
                .into(appointmentViewHolder.appointment_IV_doctorImage);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    private Appointment getItem(int position) {
        return appointments.get(position);
    }

    public class AppointmentViewHolder extends  RecyclerView.ViewHolder {

        private ImageView appointment_IV_doctorImage;
        private TextView appointment_TV_doctorName;
        private TextView appointment_TV_doctorSpecialist;
        private TextView appointment_TV_appointmentTime;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            appointment_IV_doctorImage = itemView.findViewById(R.id.appointment_IV_doctorImage);
            appointment_TV_doctorName = itemView.findViewById(R.id.appointment_TV_doctorName);
            appointment_TV_doctorSpecialist = itemView.findViewById(R.id.appointment_TV_doctorSpecialist);
            appointment_TV_appointmentTime = itemView.findViewById(R.id.appointment_TV_appointmentTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Appointment appointment = getItem(getAdapterPosition());
                    int position = getAdapterPosition();
                    onClickAppointment.onClick(appointment, position);
                }
            });
        }
    }
}
