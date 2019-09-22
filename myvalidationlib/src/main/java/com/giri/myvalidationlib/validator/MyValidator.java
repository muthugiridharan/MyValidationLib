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
import com.giri.myvalidationlib.exception.LayoutNotFoundException;
import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.model.ValidatorModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giri
 */
public class MyValidator implements ValidationTask.ValidationTaskCallback {

    private List<ValidatorModel> validatorModelList = new ArrayList<>();
    private Activity activity;
    private MyValidatorListener myValidatorListener;
    private boolean isShowError = true;

    /**
     * @param activity to get instance of Activity
     *
     */
    public MyValidator(Activity activity) {
        this.activity = activity;
    }

    /**
     * @param isShowError to display error or not
     */
    public void isSetErrorMessage(boolean isShowError) {
        this.isShowError = isShowError;
    }

    /**
     * @param password get primary view
     * @param confirmPassword get secondary view
     * @param errorMsg error message if not not valid
     */
    public void checkConfirmPassword(@NonNull View password, @NonNull View confirmPassword, @NonNull String errorMsg) {
        validatorModelList.add(new ValidatorModel("confirm_password", password, errorMsg, confirmPassword));
    }

    /**
     * @param email get email view
     * @param errorMsg error message is email invalid
     */
    public void checkEmail(@NonNull View email, @NonNull String errorMsg) {
        validatorModelList.add(new ValidatorModel("email", email, errorMsg));
    }

    /**
     * @param password get password view
     * @param type type of password
     * @param errMsg error message if password not valid
     */
    public void checkPassword(@NonNull View password, @NonNull String type, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel(type, password, errMsg));
    }

    /**
     * @param notEmpty get non empty view
     * @param errMsg error message if view is not empty
     */
    public void checkNotEmpty(@NonNull View notEmpty, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("not_null", notEmpty, errMsg));
    }

    /**
     * @param checkName get name view
     * @param errMsg error if name is invalid
     */
    public void checkName(@NonNull View checkName, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("name", checkName, errMsg));
    }


    /**
     * @param date get date view
     * @param sdfFormat Simple date format pattern to parse date
     * @param minAge minimum age
     * @param errMsg error message if date is smaller than min
     */
    public void checkDateOfBirth(@NonNull View date, @NonNull String sdfFormat, int minAge, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("date_of-birth", date, sdfFormat, minAge, errMsg));
    }

    /**
     * @param view to get the radiobutton or checkbox
     * @param errMsg error message if radiobutton and checkbox is checked
     */
    public void singleOptionsSelected(@NonNull View view, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("single_option_selected", view, errMsg));
    }

    /**
     * @param imageView to get image view
     * @param errMsg to display error message if image is null
     */
    public void checkPicture(@NonNull View imageView, @NonNull String errMsg) {
        validatorModelList.add(new ValidatorModel("check_image", imageView, errMsg));
    }

    /**
     * @param myValidatorListener listener from activity or fragment
     */
    public void setListener(MyValidatorListener myValidatorListener) {
        this.myValidatorListener = myValidatorListener;
    }

    /**
     * @param view to get view
     * @param regex regex can validate
     * @param errMsg to display ehe error if view is not valid with regex
     */
    public void checkWithRegex(@NonNull View view,@NonNull String regex,@NonNull String errMsg){
        validatorModelList.add(new ValidatorModel("check_regex",view,regex,errMsg));
    }

    /**
     * @param view to get view
     * @param min min number of character in view
     * @param max max number of character in view
     * @param errMsg error message viw have lesser than min and greater than max
     */
    public void checkMinMax(@NonNull View view,int min,int max,@NonNull String errMsg){
        validatorModelList.add(new ValidatorModel("min_max",view,min,max,errMsg));
    }

    /**
     * @param view to get view
     * @param max max number of character in view
     * @param errMsg error message viw have greater than max
     */
    public void checkMax(@NonNull View view,int max,@NonNull String errMsg){
        validatorModelList.add(new ValidatorModel("max",view,max,errMsg));
    }

    /**
     * @param view to get view
     * @param min min number of character in view
     * @param errMsg error message viw have lesser than min
     */
    public void checkMin(@NonNull View view,int min,@NonNull String errMsg){
        validatorModelList.add(new ValidatorModel("min",view,min,errMsg));
    }

    /**
     * to validate sync
     */
    public void doValidation() {
        List<ValidationResult> validationResults = new DoValidation(validatorModelList, activity, isShowError).doValidation();
        onComplete(validationResults);
    }

    /**
     * @param isAsync
     * true - to validate Async
     * false - to validate sync
     */
    public void doValidation(boolean isAsync) {
        if (isAsync) {
            ValidationTask validationTask = new ValidationTask();
            validationTask.setData(activity, isShowError, this);
            validationTask.execute(validatorModelList);
        } else {
            doValidation();
        }
    }


    /**
     * @param validationResultsList
     * when async is completed
     */
    @Override
    public void onComplete(List<ValidationResult> validationResultsList) {
        if (validationResultsList.size() == 0) {
            myValidatorListener.onSuccessValidation();
        } else {
            myValidatorListener.onValidationError(validationResultsList);
        }
    }

    /**
     * call back function
     */
    public interface MyValidatorListener {
        void onSuccessValidation();

        void onValidationError(List<ValidationResult> validationResultList);
    }
}
