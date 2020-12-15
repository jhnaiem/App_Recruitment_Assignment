package com.example.app_recruitment_assignment;

public interface ServerResponseLisenter {
    void onSuccess(String token);

    void onFailure(String message);
}
