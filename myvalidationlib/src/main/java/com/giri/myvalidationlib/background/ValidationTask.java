package com.giri.myvalidationlib.background;

import android.app.Activity;
import android.os.AsyncTask;

import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.model.ValidatorModel;
import com.giri.myvalidationlib.validator.DoValidation;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Giri
 * To do validation async
 */
public class ValidationTask extends AsyncTask<List<ValidatorModel>, ValidatorModel, List<ValidationResult>> {

    private WeakReference<Activity> weakReferenceActivity ;
    private boolean isShowError;
    private ValidationTaskCallback validationTaskCallback;


    /**
     * @param activity to get instance of Activity
     * @param isShowError isShowError to display error or not
     * @param validationTaskCallback call back function to post the complete data to MyValidator
     */
    public void setData(Activity activity,boolean isShowError,ValidationTaskCallback validationTaskCallback){
        this.weakReferenceActivity = new WeakReference<>(activity);
        this.isShowError = isShowError;
        this.validationTaskCallback = validationTaskCallback;
    }

    /**
     * @param lists getting list of ValidatorModel
     * @return lists of ValidationResult
     */
    @SafeVarargs
    @Override
    protected final List<ValidationResult> doInBackground(List<ValidatorModel>... lists) {
        List<ValidatorModel> list = lists[0];

        return new DoValidation(list,weakReferenceActivity.get(),isShowError).doValidation();
    }

    /**
     * @param validationResults to get the list of ValidationResult that return from doInBackground
     */
    @Override
    protected void onPostExecute(List<ValidationResult> validationResults) {
        validationTaskCallback.onComplete(validationResults);
    }

    /**
     * call back function to get the validation result
     */
    public interface ValidationTaskCallback{
        void onComplete(List<ValidationResult> validationResultsList);
    }


}
