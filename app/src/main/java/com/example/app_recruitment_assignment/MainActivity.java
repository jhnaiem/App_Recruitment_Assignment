package com.example.app_recruitment_assignment;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity  {


    EditText mName,mEmail,mPhone,mAddress,mUniversity, mGraduation,mCGPA,mExperience,mCurWork,mExpSalaray,mReference,mGitUrl;
    TextView mCVfile;

    Spinner mSpn_Department;

    MainViewmodel viewmodel;

    private int check;
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

        mCVfile=findViewById(R.id.cvFile);



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



        mCGPA.setFilters(new InputFilter[]{new MinMaxFilterFloat(2.0f, 4.0f, new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                return null;
            }
        })});

        mExperience.setFilters(new InputFilter[]{new MinMaxFilter("100", "0", new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                return null;
            }
        })});

//        mExpSalaray.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus){
//                    check = Integer.parseInt(mExpSalaray.getText().toString());
//                    if (15000<= check && check <=60000){
//
//                    }else{
//                        Toast.makeText(MainActivity.this,"Not in range",Toast.LENGTH_LONG).show();
//
//                    }
//
//
//                }
//
//            }
//
//        });


        mExpSalaray.setFilters(new InputFilter[]{new MinMaxFilter("15000", "60000", new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                return null;
            }
        })});



        mCVfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("application/pdf");
            }
        });


    }



    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                }
            });





}