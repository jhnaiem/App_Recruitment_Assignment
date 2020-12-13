package com.example.app_recruitment_assignment;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.function.Function;

public class MinMaxFilterFloat implements InputFilter {

    float min,max;
    Function<String,Void> callback;

    public MinMaxFilterFloat(float min, float max, Function<String,Void> callback) {
        this.min = min;
        this.max = max;
        this.callback=callback;
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            String inputString = dest.toString() + source.toString();
            float input = Float.parseFloat(inputString);
            if (inputString.length()<Float.toString(min).length() || isInRange(min,max,input)){
                return null;
            }
        }catch (NumberFormatException e){}

        callback.apply("Not in range");
        return "";

    }

    private boolean isInRange(float min, float max, float input) {

        return max > min && input >=min && input<=max ;
    }

}
