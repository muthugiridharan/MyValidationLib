package com.giri.myvalidationlib.background;

import android.app.Activity;
import android.os.AsyncTask;

import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.model.ValidatorModel;
import com.giri.myvalidationlib.validator.DoValidation;

import java.lang.ref.WeakReference;
import java.util.List;

public class ValidationTask extends AsyncTask<List<ValidatorModel>, ValidatorModel, List<ValidationResult>> {

    private WeakReference<Activity> weakReferenceActivity ;
    private boolean isShowError;
    private ValidationTaskCallback validationTaskCallback;


    public void setData(Activity activity,boolean isShowError,ValidationTaskCallback validationTaskCallback){
        this.weakReferenceActivity = new WeakReference<>(activity);
        this.isShowError = isShowError;
        this.validationTaskCallback = validationTaskCallback;
    }

    @SafeVarargs
    @Override
    protected final List<ValidationResult> doInBackground(List<ValidatorModel>... lists) {
        List<ValidatorModel> list = lists[0];

        return new DoValidation(list,weakReferenceActivity.get(),isShowError).doValidation();
    }

    @Override
    protected void onPostExecute(List<ValidationResult> validationResults) {
        validationTaskCallback.onComplete(validationResults);
    }

    public interface ValidationTaskCallback{
        void onComplete(List<ValidationResult> validationResultsList);
    }


}
