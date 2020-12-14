package com.example.app_recruitment_assignment;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Authentication {


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String url="https://recruitment.fisdev.com/api/login/";


    public void fetchResponse(LoginCallback loginCallback) throws Exception {

        OkHttpClient client = new OkHttpClient();


        String postbody= "{\n" +
                "    \"username\": \"jahid.naiem@northsouth.edu\",\n" +
                "    \"password\": \"p0DbOnz84\"\n" +
                "}";

       RequestBody body = RequestBody.create(JSON, postbody);
        // execute request
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .post(body)
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                loginCallback.onLoginFailure("Authentication failed");


            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                try {
                    JSONObject bodyJson= new JSONObject(response.body().string());

                    loginCallback.onLoginSuccess(bodyJson.getString("token"));
                } catch (JSONException e) {
                    loginCallback.onLoginFailure("Data parsing error");
                    e.printStackTrace();
                }


            }
        });
    }


}
