package com.example.app_recruitment_assignment;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Applicant {
    private String tsync_id;
    private String name;
    private String email;
    private String phone;
    private String full_address;
    private String name_of_university;
    private int graduation_year;
    private  float cgpa;
    private int experience_in_months;
    private String currrent_work_place_name;
    private String applying_in;
    private long expected_salary;
    private String field_buzz_reference;
    private String github_project_url;
    private String cv_file;
    @SerializedName("cv_file.tsync_id")
    private String cv_file_tsync_id;
    private Timestamp on_spot_update_time;
    private Timestamp on_spot_creation_time;


    public Applicant(String tsync_id, String name, String email, String phone, String full_address, String name_of_university, int graduation_year, float cgpa, int experience_in_months, String currrent_work_place_name, String applying_in, long expected_salary, String field_buzz_reference, String github_project_url, String cv_file, Timestamp on_spot_update_time, Timestamp on_spot_creation_time) {
        this.tsync_id = tsync_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.full_address = full_address;
        this.name_of_university = name_of_university;
        this.graduation_year = graduation_year;
        this.cgpa = cgpa;
        this.experience_in_months = experience_in_months;
        this.currrent_work_place_name = currrent_work_place_name;
        this.applying_in = applying_in;
        this.expected_salary = expected_salary;
        this.field_buzz_reference = field_buzz_reference;
        this.github_project_url = github_project_url;
        this.cv_file = cv_file;
        this.on_spot_update_time = on_spot_update_time;
        this.on_spot_creation_time = on_spot_creation_time;
    }



}
