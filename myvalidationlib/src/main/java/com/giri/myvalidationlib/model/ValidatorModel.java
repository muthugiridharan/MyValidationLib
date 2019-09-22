package com.giri.myvalidationlib.model;

import android.view.View;

/**
 * Created by Giri
 * POJO for all view ,it's type an error message
 */
public class ValidatorModel {

    public View errView;
    public String validationType;
    public int minNo,maxNo;
    public View primaryView;
    public String pattern;
    public String errorMsg;


    /**
     * @param validationType to get type of validation
     * @param password to get primary view
     * @param errorMsg error message to display
     * @param errView view to show error
     */
    public ValidatorModel(String validationType, View password, String errorMsg, View errView) {
        this.primaryView = password;
        this.errorMsg = errorMsg;
        this.validationType = validationType;
        this.errView = errView;
    }

    /**
     * @param validationType to get type of validation
     * @param errView view to show error
     * @param errorMsg error message to display
     */
    public ValidatorModel(String validationType,View errView, String errorMsg) {
        this.validationType = validationType;
        this.errView = errView;
        this.errorMsg = errorMsg;
    }

    /**
     * @param validationType to get type of validation
     * @param errView view to show error
     * @param sdfFormat pattern to parse date string
     * @param min min age
     * @param errorMsg error message to display
     */
    public ValidatorModel(String validationType, View errView, String sdfFormat, int min ,String errorMsg) {
        this.validationType = validationType;
        this.errView = errView;
        this.errorMsg = errorMsg;
        this.pattern = sdfFormat;
        this.minNo = min;
    }

    /**
     * @param validationType to get type of validation
     * @param errView view to show error
     * @param regex pattern to validate view
     * @param errorMsg error message to display
     */
    public ValidatorModel(String validationType, View errView, String regex,String errorMsg) {
        this.validationType = validationType;
        this.errView = errView;
        this.errorMsg = errorMsg;
        this.pattern = regex;
    }

    /**
     * @param validationType to get type of validation
     * @param errView view to show error
     * @param minNo min number of character in view
     * @param maxNo max number of character in view
     * @param errorMsg error message to display
     */
    public ValidatorModel(String validationType,View errView,int minNo,int maxNo,String errorMsg){
        this.validationType = validationType;
        this.errView = errView;
        this.errorMsg = errorMsg;
        this.minNo = minNo;
        this.maxNo = maxNo;
    }

    /**
     * @param validationType to get type of validation
     * @param errView view to show error
     * @param maxNo max no of character in view
     * @param errorMsg error message to display
     */
    public ValidatorModel(String validationType,View errView,int maxNo,String errorMsg){
        this.validationType = validationType;
        this.errView = errView;
        this.errorMsg = errorMsg;
        this.maxNo = maxNo;
    }

}
