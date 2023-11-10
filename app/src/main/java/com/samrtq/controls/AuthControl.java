package com.samrtq.controls;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.samrtq.App;
import com.samrtq.callback.AuthCallBack;
import com.samrtq.utils.AuthUser;

public class AuthControl {
    private AuthUser user;
    private FirebaseAuth auth;
    private AuthCallBack authCallBack;

    public AuthControl(AuthUser user) {
        this.user = user;
        this.auth = FirebaseAuth.getInstance();

    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }

    public void signIn() {
        //login

    }

    public void signUp() {
        // create new account
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    authCallBack.onCreateAccountComplete(true, "");
                }else {
                    authCallBack.onCreateAccountComplete(false, task.getException().getMessage().toString());
                }
            }
        });
    }

    public void signOut(){
        // logout

    }

}
