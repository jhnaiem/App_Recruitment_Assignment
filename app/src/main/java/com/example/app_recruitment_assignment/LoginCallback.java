package com.example.app_recruitment_assignment;

public interface LoginCallback {
    void onLoginSuccess(String token);

    void onLoginFailure(String message);
}
