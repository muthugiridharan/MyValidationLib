package com.giri.myvalidationlib.validator;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.giri.myvalidationlib.background.ValidationTask;
import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.model.ValidatorModel;
import com.giri.myvalidationlib.exception.LayoutNotFoundException;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MyValidator implements ValidationTask.ValidationTaskCallback {

    private List<ValidatorModel> validatorModelList = new ArrayList<>();
    private Activity activity;
    private MyValidatorListener myValidatorListener;
    private boolean isShowError = true;

    public MyValidator(Activity activity) {
        this.activity = activity;
    }

    public void isSetErrorMessage(boolean isShowError) {
        this.isShowError = isShowError;
    }

    public void checkConfirmPassword(@NonNull View password, @NonNull View confirmPassword, @NonNull String errorMsg) {
        validatorModelList.add(new ValidatorModel("confirm_password", getText(password), getText(confirmPassword), errorMsg, confirmPassword));
    }

    public void checkEmail(@NonNull View email, @NonNull String errorMsg) {
        validatorModelList.add(new ValidatorModel("email", getText(email), email, errorMsg));
    }

    public void checkPassword(@NonNull View password, @NonNull String type, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel(type, getText(password), password, errMsg));
    }

    public void notEmpty(@NonNull View notEmpty, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("not_null", getText(notEmpty), notEmpty, errMsg));
    }

    public void checkName(@NonNull View checkName, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("name", getText(checkName), checkName, errMsg));
    }


    public void checkDateOfBirth(@NonNull View date, @NonNull String sdfFormat, int minAge, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("date_of-birth", getText(date), date, sdfFormat, minAge, errMsg));
    }

    public void singleOptionsSelected(@NonNull View view, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("single_option_selected", isSingleOptionSelected(view), view, errMsg));
    }

    public void checkPicture(@NonNull View imageView, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("check_image", isSingleOptionSelected(imageView), imageView, errMsg));
    }

    public void setListner(MyValidatorListener myValidatorListener) {
        this.myValidatorListener = myValidatorListener;
    }

    public void doValidation() {
        List<ValidationResult> validationResults = new DoValidation(validatorModelList, activity, isShowError).doValidation();
        onComplete(validationResults);
    }

    public void doValidation(boolean isAsync) {
        if (isAsync) {
            ValidationTask validationTask = new ValidationTask();
            validationTask.setData(activity, isShowError, this);
            validationTask.execute(validatorModelList);
        } else {
            doValidation();
        }
    }


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

    @Override
    public void onComplete(List<ValidationResult> validationResultsList) {
        if (validationResultsList.size() == 0) {
            myValidatorListener.onSuccessValidation();
        } else {
            myValidatorListener.onValidationError(validationResultsList);
        }
    }

    public interface MyValidatorListener {
        void onSuccessValidation();

        void onValidationError(List<ValidationResult> validationResultList);
    }
}
