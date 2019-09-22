package com.giri.myvalidationlib.validator;

import android.app.Activity;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.giri.myvalidationlib.exception.LayoutNotFoundException;
import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.model.ValidatorModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Giri
 */
public class DoValidation {

    private List<ValidatorModel> validatorModelList;
    private Activity activity;
    private List<ValidationResult> validationResultsList = new ArrayList<>();
    private boolean isShowError;

    /**
     * @param validatorModelList list of ValidatorModel that contains views and other data
     * @param activity get instance of activity
     * @param isShowError show error in view
     */
    public DoValidation(List<ValidatorModel> validatorModelList, Activity activity, boolean isShowError) {
        this.validatorModelList = validatorModelList;
        this.activity = activity;
        this.isShowError = isShowError;
    }

    /**
     * @return list of ValidationResult
     */
    public List<ValidationResult> doValidation() {
        validationResultsList.clear();
        for (ValidatorModel validatorModel : validatorModelList) {
            validation(validatorModel);
        }
        return validationResultsList;
    }

    /**
     * @param validatorModel to switch the validation type
     */
    private void validation(final ValidatorModel validatorModel) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (validatorModel.validationType) {
                    case "email":
                        emailValidation(validatorModel.errView, validatorModel.errorMsg);
                        break;
                    case "basic_password":
                        checkRegex(validatorModel.errView, Constants.B_PASSWORD, validatorModel.errorMsg);
                        break;
                    case "medium_password":
                        checkRegex(validatorModel.errView, Constants.M_PASSWORD, validatorModel.errorMsg);
                        break;
                    case "strong_password":
                        checkRegex(validatorModel.errView, Constants.H_PASSWORD, validatorModel.errorMsg);
                        break;
                    case "not_null":
                        notNull(validatorModel.errView, validatorModel.errorMsg);
                        break;
                    case "name":
                        checkName(validatorModel.errView, validatorModel.errorMsg);
                        break;
                    case "min_max":
                        checkMinMaxLength(validatorModel.errView,validatorModel.minNo,validatorModel.maxNo,validatorModel.errorMsg);
                        break;
                    case "max":
                        checkMax(validatorModel.errView,validatorModel.maxNo,validatorModel.errorMsg);
                        break;
                    case "min":
                        checkMin(validatorModel.errView,validatorModel.maxNo,validatorModel.errorMsg);
                        break;
                    case "confirm_password":
                        confirmPassword(validatorModel.primaryView, validatorModel.errView, validatorModel.errorMsg);
                        break;
                    case "date_of-birth":
                        if (isValidAge(getText(validatorModel.errView), validatorModel.pattern, validatorModel.minNo)) {
                            setErrorMsg(validatorModel.errView, null);
                        } else {
                            addErrorList(validatorModel.errView, validatorModel.errorMsg);
                        }
                        break;
                    case "check_regex":
                        checkRegex(validatorModel.errView, validatorModel.pattern, validatorModel.errorMsg);
                        break;
                    case "single_option_selected":
                    case "check_image":
                        if (!isSingleOptionSelected(validatorModel.errView)) {
                            addErrorList(validatorModel.errView, validatorModel.errorMsg);
                        } else {
                            setErrorMsg(validatorModel.errView, null);
                        }
                        break;
                }
            }
        });
    }

    /**
     * @param errView to display error message
     * @param minNo minimum number of character
     * @param maxNo max number of character
     * @param errorMsg error message to display
     */
    private void checkMinMaxLength(View errView, int minNo, int maxNo, String errorMsg) {
        String text = getText(errView).trim();
        if(text.length() >= minNo && text.length() <= maxNo){
            setErrorMsg(errView, null);
        }else{
            addErrorList(errView,errorMsg);
        }
    }

    /**
     * @param errView to display error message
     * @param maxNo maximum number of character
     * @param errorMsg error message to display
     */
    private void checkMax(View errView,int maxNo,String errorMsg){
        String text = getText(errView).trim();
        if(text.length() >= maxNo){
            setErrorMsg(errView, null);
        }else{
            addErrorList(errView,errorMsg);
        }
    }

    /**
     * @param errView to display error message
     * @param minNo minimum number of character
     * @param errorMsg error message to display
     */
    private void checkMin(View errView,int minNo,String errorMsg){
        String text = getText(errView).trim();
        if(text.length() <= minNo){
            setErrorMsg(errView, null);
        }else{
            addErrorList(errView,errorMsg);
        }
    }


    /**
     * @param date date of birth
     * @param dateFormat pattern to format date
     * @param validAge min age
     *
     * @return if it is valid age
     */
    private boolean isValidAge(String date, String dateFormat, int validAge) {
        try {
            Date birthDate = new SimpleDateFormat(dateFormat, Locale.getDefault()).parse(date);
            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            dob.setTime(birthDate);
            int monthToday = today.get(Calendar.MONTH) + 1;
            int monthDOB = dob.get(Calendar.MONTH) + 1;
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (age > validAge) {
                return true;
            } else if (age == validAge) {
                if (monthDOB > monthToday) {
                    return true;
                } else if (monthDOB == monthToday) {
                    int todayDate = today.get(Calendar.DAY_OF_MONTH);
                    int dobDate = dob.get(Calendar.DAY_OF_MONTH);
                    return dobDate <= todayDate;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param view view like checkbox and radio button
     *
     * @return if it is checked
     */
    private boolean isSingleOptionSelected(View view) {
        boolean isValid = false;
        if (view instanceof RadioGroup) {
            RadioGroup rg = (RadioGroup) view;
            RadioButton radioButton = rg.findViewById(rg.getCheckedRadioButtonId());
            if (radioButton != null)
                isValid = true;
            return isValid;
        } else if (view instanceof CheckBox) {
            CheckBox cb = (CheckBox) view;
            return cb.isChecked();
        } else if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (iv.getDrawable() != null)
                isValid = true;
            return isValid;
        }

        throw new LayoutNotFoundException("BadLayout : required layout not found");
    }

    /**
     * @param passwordView view to get primary text
     * @param errLayout view to get secondary text
     * @param errMsg if primary text not equal to secondary text
     */
    private void confirmPassword(View passwordView, View errLayout, String errMsg) {
        String password = getText(passwordView);
        String confirmPassword = getText(errLayout);
        if (!password.equals(confirmPassword)) {
            addErrorList(errLayout, errMsg);
        } else {
            setErrorMsg(errLayout, null);
        }
    }

    /**
     * @param errView view to show error and get text
     * @param errMsg error message if text is not empty
     */
    private void notNull(View errView, String errMsg) {
        if (getText(errView).trim().length() == 0) {
            addErrorList(errView, errMsg);
        } else {
            setErrorMsg(errView, null);
        }
    }

    /**
     * @param errView view to show error and get text
     * @param errMsg error message if text is not empty
     */
    private void checkName(View errView, String errMsg) {
        String text = getText(errView);
        if (text.trim().length() < 3 && !text.matches(Constants.CONTAIN_NUMBER)) {
            addErrorList(errView, errMsg);
        } else {
            setErrorMsg(errView, null);
        }
    }

    /**
     * @param errView view to show error and get text
     * @param regex regex to validate
     * @param errMsg error message if text is not empty
     */
    private void checkRegex(View errView, String regex, String errMsg) {
        if (!getText(errView).matches(regex)) {
            addErrorList(errView, errMsg);
        } else {
            setErrorMsg(errView, null);
        }
    }

    /**
     * @param errView view to show error and get text
     * @param errMsg error message if text is not empty
     */
    private void emailValidation(View errView, String errMsg) {
        if (!Patterns.EMAIL_ADDRESS.matcher(getText(errView)).matches()) {
            addErrorList(errView, errMsg);
        } else {
            setErrorMsg(errView, null);
        }
    }

    /**
     * @param errView view to show error and get text
     * @param errMsg error message if text is not empty
     */
    private void addErrorList(View errView, String errMsg) {
        validationResultsList.add(new ValidationResult(errView, errMsg));
        if (isShowError) {
            setErrorMsg(errView, errMsg);
        } else {
            setErrorMsg(errView, null);
        }
    }

    /**
     * @param view view that contain edit text to get text
     *
     * @return string that get from view
     */
    private String getText(View view) {
        if (view instanceof EditText) {
            return String.valueOf(((EditText) view).getText());
        } else if (view instanceof TextInputLayout) {
            EditText editText = ((TextInputLayout) view).getEditText();
            if (editText != null) {
                return String.valueOf(editText.getText());
            }
        }
        throw new LayoutNotFoundException("BadLayout : EditText not found");
    }

    /**
     * @param view error showing view
     * @param errMsg error message that shows in view
     */
    private void setErrorMsg(final View view, final String errMsg) {

        if (view instanceof EditText) {
            EditText editText = ((EditText) view);
            if (errMsg == null) {
                if (editText.getError() != null) {
                    editText.setError(null);
                }
                return;
            }
            editText.setError(errMsg);
        }

        if (view instanceof TextInputLayout) {
            TextInputLayout textInputLayout = ((TextInputLayout) view);
            if (errMsg == null) {
                if (textInputLayout.getError() != null) {
                    textInputLayout.setError(null);
                }
                return;
            }
            textInputLayout.setError(errMsg);
        }
    }

}
