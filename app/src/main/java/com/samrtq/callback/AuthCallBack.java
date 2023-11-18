package com.samrtq.callback;

public interface AuthCallBack {
    void onCreateAccountComplete(boolean status, String msg);
    void onLoginComplete(boolean status, String msg);

}
