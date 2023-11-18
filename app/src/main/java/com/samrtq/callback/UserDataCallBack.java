package com.samrtq.callback;

import com.samrtq.entities.User;

public interface UserDataCallBack {
    void onSaveUserComplete(boolean status, String msg);
    void onUserDataFetchComplete(User user);
}
