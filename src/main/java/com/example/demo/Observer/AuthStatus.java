package com.example.demo.Observer;

import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class AuthStatus {
    private static AuthStatus instance;
    private boolean isLoggedIn = false;
    private User currentUser;
    private final List<AuthObserver> observers = new ArrayList<>();

    private AuthStatus() {}

    public static AuthStatus getInstance() {
        if (instance == null) {
            instance = new AuthStatus();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setLoginStatus(boolean isLoggedIn, User user) {
        this.isLoggedIn = isLoggedIn;
        this.currentUser = user;
        notifyObservers();
    }

    public void addObserver(AuthObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AuthObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (AuthObserver observer : observers) {
            observer.onLoginStatusChanged(isLoggedIn, currentUser);
        }
    }
}
