package com.samrtq.controls;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.samrtq.callback.AuthCallBack;
import com.samrtq.auth.AuthUser;

public class AuthControl {
    private FirebaseAuth auth;
    private AuthCallBack authCallBack;

    public AuthControl() {
        this.auth = FirebaseAuth.getInstance();

    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }

    public void signIn(AuthUser user) {
        //login

        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    authCallBack.onLoginComplete(true, "Login success");
                }else {
                    authCallBack.onLoginComplete(false, task.getException().getMessage().toString());
                }
            }
        });
    }

    public void signUp(AuthUser user) {
        // create new account
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    authCallBack.onCreateAccountComplete(true, "Account created successfully");
                }else {
                    authCallBack.onCreateAccountComplete(false, task.getException().getMessage().toString());
                }
            }
        });
    }

    public void signOut(){
        // logout
        auth.signOut();
    }

    public FirebaseUser getCurrentUser(){
       return auth.getCurrentUser();
    }
}
