package com.example.app_recruitment_assignment;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app_recruitment_assignment.fileutils.FileUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * permissions request code
     */
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};


    EditText mName, mEmail, mPhone, mAddress, mUniversity, mGraduation, mCGPA, mExperience, mCurWork, mExpSalaray, mReference, mGitUrl;
    TextView mCVfile;
    Button mSubmit;

    ProgressBar progressBar;

    Spinner mSpn_Department;

    MainViewmodel viewmodel;

    Authentication authentication = new Authentication();
    private Uri uri;

    private String tokenAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewmodel = new ViewModelProvider(this).get(MainViewmodel.class);

        mName = findViewById(R.id.et_name);
        mEmail = findViewById(R.id.et_email);
        mPhone = findViewById(R.id.et_phone);
        mAddress = findViewById(R.id.et_address);
        mUniversity = findViewById(R.id.et_university);
        mGraduation = findViewById(R.id.et_graduation);
        mCGPA = findViewById(R.id.et_cgpa);
        mExperience = findViewById(R.id.et_experience);
        mCurWork = findViewById(R.id.et_currentWork);
        mSpn_Department = findViewById(R.id.spn_Department);
        mExpSalaray = findViewById(R.id.et_expSalary);
        mReference = findViewById(R.id.et_reference);
        mGitUrl = findViewById(R.id.et_gitUrl);

        mCVfile = findViewById(R.id.cvFile);

        mSubmit = findViewById(R.id.btn_submit);

        progressBar = findViewById(R.id.progressBar);

        mSpn_Department.setPrompt("Select Applying Department");
        List<String> departments = new ArrayList<String>();
        //departments.add("    Select Applying Department");
        departments.add("Mobile");
        departments.add("Backend");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, departments);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpn_Department.setAdapter(dataAdapter);


        final String selectedDepartment = mSpn_Department.getSelectedItem().toString();


        mGraduation.setFilters(new InputFilter[]{new MinMaxFilter("2015", "2020", new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                return null;
            }
        })});


        mCGPA.setFilters(new InputFilter[]{new MinMaxFilterFloat(2.0f, 4.0f, new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                return null;
            }
        })});

        mExperience.setFilters(new InputFilter[]{new MinMaxFilter("100", "0", new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                return null;
            }
        })});


        mExpSalaray.setFilters(new InputFilter[]{new MinMaxFilter("15000", "60000", new Function<String, Void>() {
            @Override
            public Void apply(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                return null;
            }
        })});


        mCVfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermissions();
                mGetContent.launch("application/pdf");
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                mSubmit.setVisibility(View.GONE);

                viewmodel.onSubmitClick(mName.getText().toString(),
                        mEmail.getText().toString(),
                        mPhone.getText().toString(),
                        mAddress.getText().toString(),
                        mUniversity.getText().toString(),
                        mGraduation.getText().toString(),
                        mCGPA.getText().toString(),
                        mExperience.getText().toString(),
                        mCurWork.getText().toString(),
                        selectedDepartment,
                        mExpSalaray.getText().toString(),
                        mReference.getText().toString(),
                        mGitUrl.getText().toString(),
                        mCVfile.getText().toString(),
                        new ServerResponseLisenter() {
                            @Override
                            public void onSuccess(String token) {

                                // Make url by adding the token received
                                String putURL = "https://recruitment.fisdev.com/api/file-object/" + token + "/";

                                // Method to Upload CV file
                                uploadCvFile(putURL);

                            }

                            @Override
                            public void onFailure(String message) {

                                // UI operations needs to be run on main thread
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        });


            }
        });


        viewmodel.validationMessageLiveData.observe(this, (message) -> {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        });

        //Calling the function for Authentication and get the Auth token
        viewmodel.fetchToken();


    }

    //Method to upload cv file
    private void uploadCvFile(String putURL) {


        File cvFile = FileUtils.getFile(MainActivity.this, uri);

        Log.d("Path name", cvFile.getAbsolutePath());
        if (cvFile.exists() && !cvFile.isDirectory()) {
            Log.d("File", "Exists");
        } else {
            Log.d("File", "File not found");
        }
        String fileName = FileUtils.getFileName(MainActivity.this, uri);


        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, cvFile);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, body)
                .build();


       // Get the Auth token from MainViewmodel
        String tokenAuth = viewmodel.getMtoken();

        Request request = new Request.Builder()
                .url(putURL)
                .addHeader("Authorization", "Token " + tokenAuth)
                .addHeader("Cookie", "multidb_pin_writes=y")
                .put(requestBody)
                .build();

        authentication.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                setupViewsafterDataProcessing();
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                setupViewsafterDataProcessing();


                Log.d("CVFileUpload Response", response.body().string());


            }
        });


    }

    // To run on UI thread
    private void setupViewsafterDataProcessing() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                mSubmit.setVisibility(View.VISIBLE);
            }
        });
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    MainActivity.this.uri = uri;
                    // Handle the returned Uri
                    //Calling the Method to get file name from URI and then set it on textview
                    mCVfile.setText(FileUtils.getFileName(MainActivity.this, uri));


                }
            });


    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }

                break;
        }
    }


}