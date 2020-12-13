package com.example.app_recruitment_assignment;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewmodel extends ViewModel {

    MutableLiveData<String> validationMessageLiveData= new MutableLiveData<>();

    public void onSubmitClick(String name, String email, String phone, String address, String university, String graduation, String cgpa, String experience, String curWorkplace, String selectedDepartment, String expSalary, String reference, String gitUrl, String cvfile){

        if (name.isEmpty()){
            validationMessageLiveData.setValue("Name is required");
        }else if(email.isEmpty()){
            validationMessageLiveData.setValue("Email is required");
        }else if(phone.isEmpty()){
            validationMessageLiveData.setValue("Phone number is required");
        }else if(address.isEmpty()){
            validationMessageLiveData.setValue("Address number is required");
        }else if(university.isEmpty()){
            validationMessageLiveData.setValue("Phone number is required");
        }else if (graduation.isEmpty()){
            validationMessageLiveData.setValue("Graduation year is required");
        }


    }


}
