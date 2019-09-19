package com.giri.myvalidationlib.model;

import android.view.View;

public class ValidationResult {

    public View view;
    public String errMsg;

    public ValidationResult(View view,String errMsg){
        this.view = view;
        this.errMsg = errMsg;
    }
}
