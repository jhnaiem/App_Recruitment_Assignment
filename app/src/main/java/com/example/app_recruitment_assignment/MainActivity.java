package com.example.app_recruitment_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity  {


    EditText mName,mEmail,mPhone,mAddress,mUniversity, mGraduation,mCGPA,mExperience,mCurWork,mExpSalaray,mReference,mGitUrl;

    Spinner mSpn_Department;

    MainViewmodel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewmodel= new ViewModelProvider(this).get(MainViewmodel.class);

        mName=findViewById(R.id.et_name);
        mEmail=findViewById(R.id.et_email);
        mPhone=findViewById(R.id.et_phone);
        mAddress=findViewById(R.id.et_address);
        mUniversity=findViewById(R.id.et_university);
        mGraduation=findViewById(R.id.et_graduation);
        mCGPA=findViewById(R.id.et_cgpa);
        mExperience=findViewById(R.id.et_experience);
        mCurWork=findViewById(R.id.et_currentWork);
        mSpn_Department =findViewById(R.id.spn_Department);
        mExpSalaray=findViewById(R.id.et_expSalary);
        mReference=findViewById(R.id.et_reference);
        mGitUrl=findViewById(R.id.et_gitUrl);


        mGraduation=findViewById(R.id.et_graduation);

        mSpn_Department.setPrompt("Select Applying Department");
        List<String> departments= new ArrayList<String>();
        //departments.add("    Select Applying Department");
        departments.add("Mobile");
        departments.add("Backend");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,departments);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpn_Department.setAdapter(dataAdapter);


        mSpn_Department.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mGraduation.setFilters(new  InputFilter[]{new MinMaxFilter("2015", "2020", new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                return null;
            }
        })});

    }





}