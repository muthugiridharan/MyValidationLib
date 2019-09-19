package com.giri.myvalidationlib.validator;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.giri.myvalidationlib.exception.LayoutNotFoundException;
import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.model.ValidatorModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DoValidation {

    private List<ValidatorModel> validatorModelList;
    private Activity activity;
    private List<ValidationResult> validationResultsList = new ArrayList<>();
    private boolean isShowError;

    public DoValidation(List<ValidatorModel> validatorModelList, Activity activity, boolean isShowError) {
        this.validatorModelList = validatorModelList;
        this.activity = activity;
        this.isShowError = isShowError;
    }

    public List<ValidationResult> doValidation() {
        for (ValidatorModel validatorModel : validatorModelList) {
            validation(validatorModel);
        }
        return validationResultsList;
    }

    private void validation(ValidatorModel validatorModel) {
        switch (validatorModel.validationType) {
            case "email":
                emailValidation(validatorModel.text, validatorModel.errView, validatorModel.errorMsg);
                break;
            case "basic_password":
                checkRegex(validatorModel.text, validatorModel.errView, Constants.B_PASSWORD, validatorModel.errorMsg);
                break;
            case "medium_password":
                checkRegex(validatorModel.text, validatorModel.errView, Constants.M_PASSWORD, validatorModel.errorMsg);
                break;
            case "strong_password":
                checkRegex(validatorModel.text, validatorModel.errView, Constants.H_PASSWORD, validatorModel.errorMsg);
                break;
            case "not_null":
                notNull(validatorModel.text, validatorModel.errView, validatorModel.errorMsg);
                break;
            case "name":
                checkName(validatorModel.text, validatorModel.errView, validatorModel.errorMsg);
                break;
            case "confirm_password":
                confirmPassword(validatorModel.text,validatorModel.confirmPassword,validatorModel.errView,validatorModel.errorMsg);
                break;
            case "date_of-birth":
                String age = getAge(validatorModel.text,validatorModel.pattern);
                if(TextUtils.isDigitsOnly(age)) {
                    if (Integer.parseInt(age) > validatorModel.minNo) {
                        addErrorList(validatorModel.errView, validatorModel.errorMsg);
                    }else{
                        setErrorMsg(validatorModel.errView,null);
                    }
                }else{
                    addErrorList(validatorModel.errView, validatorModel.errorMsg);
                }
                break;
            case "single_option_selected":
            case "check_image":
                System.out.println("isVali==>%s"+validatorModel.isSelected);
                if(! validatorModel.isSelected){
                    addErrorList(validatorModel.errView,validatorModel.errorMsg);
                }
                break;
        }
    }

    private String getAge(String text,String sdf){

        if(!TextUtils.isEmpty(text) && !TextUtils.isEmpty(sdf)) {
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            try {
                Date date = new SimpleDateFormat(sdf, Locale.getDefault()).parse(text);
                dob.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dob.after(now)) {
                throw new LayoutNotFoundException("DOB should be lesser than current date");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            int age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                return String.valueOf(--age);
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    return String.valueOf(--age);
                }
            }
        }
        return "error";
    }


    private void confirmPassword(String password,String confirmPassword,View errLayout,String errMsg){
        if(!password.equals(confirmPassword)){
            addErrorList(errLayout,errMsg);
        }else{
            setErrorMsg(errLayout,null);
        }
    }

    private void notNull(String text, View errView, String errMsg) {
        if (text == null || text.trim().length() == 0) {
            addErrorList(errView,errMsg);
        }else{
            setErrorMsg(errView,null);
        }
    }

    private void checkName(String text, View errView, String errMsg) {
        if (text.trim().length() < 3 && !text.matches(Constants.CONTAIN_NUMBER)) {
            addErrorList(errView,errMsg);
        }else{
            setErrorMsg(errView,null);
        }
    }

    private void checkRegex(String password, View errView, String regex, String errMsg) {
        if (!password.matches(regex)) {
            addErrorList(errView,errMsg);
        }else{
            setErrorMsg(errView,null);
        }
    }

    private void emailValidation(String email, View errView, String errMsg) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            addErrorList(errView,errMsg);
        }else{
            setErrorMsg(errView,null);
        }
    }

    private void addErrorList(View errView,String errMsg){
        validationResultsList.add(new ValidationResult(errView, errMsg));
        if (isShowError) {
            setErrorMsg(errView, errMsg);
        }else{
            setErrorMsg(errView,null);
        }
    }

    private void setErrorMsg(final View view, final String errMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (view instanceof EditText) {
                    EditText editText= ((EditText) view);
                    if(errMsg == null){
                        if(editText.getError() != null){
                            editText.setError(null);
                        }
                        return;
                    }
                    editText.setError(errMsg);
                }

                if (view instanceof TextInputLayout) {
                    TextInputLayout textInputLayout = ((TextInputLayout) view);
                    if(errMsg == null){
                        if(textInputLayout.getError() != null){
                            textInputLayout.setError(null);
                        }
                        return;
                    }
                    textInputLayout.setError(errMsg);
                }
            }
        });
    }

}
