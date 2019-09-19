package com.giri.myvalidationlib.model;

import android.view.View;

public class ValidatorModel {

    public View errView;
    public String validationType;
    public Integer minNo, maxMax;
    public String passwordType;
    public String confirmPassword;
    public String text;
    public String pattern;
    public String errorMsg;
    public boolean isSelected;

    public ValidatorModel(String validationType, String password, String confirmPassword, String errorMsg, View errView) {
        this.confirmPassword = confirmPassword;
        this.text = password;
        this.errorMsg = errorMsg;
        this.validationType = validationType;
        this.errView = errView;
    }

    public ValidatorModel(View view, String validationType, String errorMsg) {

    }


    public ValidatorModel(String validationType, String text, View errView, String errorMsg) {
        this.validationType = validationType;
        this.text = text;
        this.errView = errView;
        this.errorMsg = errorMsg;
    }

    public ValidatorModel(String validationType, String text, View errView, String sdfFormat, int min ,String errorMsg) {
        this.validationType = validationType;
        this.text = text;
        this.errView = errView;
        this.errorMsg = errorMsg;
        this.pattern = sdfFormat;
        this.minNo = min;
    }

    public ValidatorModel(String validationType, boolean singleOptionSelected,View errView, String errMsg) {
        this.validationType = validationType;
        this.errorMsg = errMsg;
        this.errView = errView;
        this.isSelected = singleOptionSelected;
    }


}
