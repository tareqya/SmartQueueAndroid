package com.samrtq.controls;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samrtq.callback.UserDataCallBack;
import com.samrtq.entities.User;
import com.samrtq.utils.Constants;

public class UserControl {
    private DatabaseReference mDatabase;
    private UserDataCallBack userDataCallBack;

    public UserControl(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void setUserDataCallBack(UserDataCallBack userDataCallBack){
        this.userDataCallBack = userDataCallBack;
    }

    public void SaveUserData (User user) {
        mDatabase.child(Constants.USERS_TABLE).child(user.getId())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            userDataCallBack.onSaveUserComplete(true, "User data saved");
                        }else{
                            userDataCallBack.onSaveUserComplete(false, task.getException().getMessage());
                        }
                    }
                });
    }

    public void getUserData(String uid){
        mDatabase.child(Constants.USERS_TABLE).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userDataCallBack.onUserDataFetchComplete(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
