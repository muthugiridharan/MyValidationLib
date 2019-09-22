package com.giri.myvalidationlib.model;

import android.view.View;

/**
 * Create by Giri
 * validation result POJO
 */
public class ValidationResult {

    public View view;
    public String errMsg;

    /**
     * @param view to show error message
     * @param errMsg error message for invalid view
     */
    public ValidationResult(View view,String errMsg){
        this.view = view;
        this.errMsg = errMsg;
    }
}
