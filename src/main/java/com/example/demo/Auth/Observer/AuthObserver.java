package com.example.demo.Auth.Observer;

import com.example.demo.model.User;

public interface AuthObserver {
    void onLoginStatusChanged(boolean isLoggedIn, User currentUser);
}
