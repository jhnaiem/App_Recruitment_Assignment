package com.example.app_recruitment_assignment;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.function.Function;

public class MinMaxFilter implements InputFilter {

    private int min, max;
    Function<String,Void> callback;



    public MinMaxFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public MinMaxFilter(String min, String max, Function<String,Void> callback) {
        this.min=Integer.parseInt(min);
        this.max= Integer.parseInt(max);
        this.callback=callback;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        try {

            String inputString = dest.toString() + source.toString();
            int input = Integer.parseInt(inputString);
            if (inputString.length()<4 || isInRange(min,max,input)){
                return null;
            }
        }catch (NumberFormatException e){}

        callback.apply("Not in range");
             return "";

    }

    private boolean isInRange(int min, int max, int input) {
        return max > min && input >=min && input<=max ;
    }




}
