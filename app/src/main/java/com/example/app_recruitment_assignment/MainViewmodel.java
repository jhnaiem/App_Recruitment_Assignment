package com.example.app_recruitment_assignment;

import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class MainViewmodel extends ViewModel {

    String token;

    MutableLiveData<String> validationMessageLiveData = new MutableLiveData<>();

    public void onSubmitClick(String name, String email, String phone, String address, String university, String graduation, String cgpa, String experience, String curWorkplace, String selectedDepartment, String expSalary, String reference, String gitUrl, String cvfile) {

        if (name.isEmpty()) {
            validationMessageLiveData.setValue("Name is required");
        } else if (email.isEmpty()) {
            validationMessageLiveData.setValue("Email is required");
        } else if (phone.isEmpty()) {
            validationMessageLiveData.setValue("Phone number is required");
        } else if (university.isEmpty()) {
            validationMessageLiveData.setValue("Phone number is required");
        } else if (graduation.isEmpty()) {
            validationMessageLiveData.setValue("Graduation year is required");
        } else if (Integer.parseInt(graduation) < 2015 && Integer.parseInt(graduation) > 2020) {
            validationMessageLiveData.setValue("Graduation must be between 2015 to 2020");
        } else if (Float.parseFloat(cgpa) < 2.0 && Integer.parseInt(cgpa) > 4.0) {
            validationMessageLiveData.setValue("CGPA must be between 2.0 to 4.0");
        } else if (Integer.parseInt(experience) < 0 && Integer.parseInt(experience) > 100) {
            validationMessageLiveData.setValue("Experience must be between 0 to 100 months");
        } else if (selectedDepartment.isEmpty()) {
            validationMessageLiveData.setValue("Applying department  is required");
        } else if (expSalary.isEmpty()) {
            validationMessageLiveData.setValue("Expected salary  is required");
        } else if (Long.parseLong(expSalary) < 15000 && Long.parseLong(expSalary) > 60000) {
            validationMessageLiveData.setValue("Expected salary  must be between 15000 to 60000");
        } else if (gitUrl.isEmpty()) {
            validationMessageLiveData.setValue("Github project URL  is required");
        } else if (cvfile.isEmpty()) {
            validationMessageLiveData.setValue("CV file  is required");
        } else {







        }


    }



    public void fetchToken() {
        Authentication authentication = new Authentication();

        LoginCallback loginCallback = new LoginCallback() {
            @Override
            public void onLoginSuccess(String token) {

                MainViewmodel.this.token=token;
                Log.d("===>", " success: " + token);

            }

            @Override
            public void onLoginFailure(String message) {
                Log.d("===>", " failure: " + message);

            }
        };

        try {
            authentication.fetchResponse(loginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
