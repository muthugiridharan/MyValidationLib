package com.giri.validationproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.validator.MyValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyValidator.MyValidatorListener{

    private TextInputLayout tlFirstName,tlLastName,tlUserName,tlEmail,tlPassword,tlConfirmPassword,tlDateOfBirth,tlDes;
    private RadioGroup rgGender;
    private MaterialCheckBox cbTerms;
    private ImageView ivDp;
    private MyValidator myValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MaterialButton btnSubmit = findViewById(R.id.btnSubmit);
        final MaterialButton btnSetImage = findViewById(R.id.btnSetImage);
        ivDp = findViewById(R.id.ivDp);
        tlFirstName = findViewById(R.id.tlFirstName);
        tlLastName =findViewById(R.id.tlLastName);
        tlUserName = findViewById(R.id.tlUserName);
        tlPassword = findViewById(R.id.tlPassword);
        tlEmail = findViewById(R.id.tlEmail);
        rgGender = findViewById(R.id.rgGender);
        cbTerms = findViewById(R.id.cbTerms);
        tlConfirmPassword = findViewById(R.id.tlConfirmPassword);
        tlDateOfBirth = findViewById(R.id.tlDateOfBirth);
        tlDes = findViewById(R.id.tlDes);
        Activity activity = this;
        myValidator = new MyValidator(activity);
        myValidator.setListener(this);
        myValidator.isSetErrorMessage(true);
        btnSetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSetImage.getText().equals("SET IMAGE")){
                    ivDp.setImageDrawable(getResources().getDrawable(R.drawable.placeholde));
                    btnSetImage.setText("CLEAR IMAGE");
                }else{
                    ivDp.setImageDrawable(null);
                    btnSetImage.setText("SET IMAGE");
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doValidation();
            }
        });
        setValidation();
    }

    private void setValidation() {
        myValidator.checkEmail(tlEmail,"Enter valid email");
        myValidator.checkName(tlFirstName,"Enter FirstName");
        myValidator.checkName(tlLastName,"Enter Last Name");
        myValidator.checkNotEmpty(tlUserName,"Enter User Name");
        myValidator.checkPassword(tlPassword,"medium_password","Enter valid password");
        myValidator.checkConfirmPassword(tlPassword,tlConfirmPassword,"Password mismatch");
        myValidator.checkDateOfBirth(tlDateOfBirth,"dd-MM-yyyy",18,"Age must be above 18");
        myValidator.singleOptionsSelected(rgGender,"Select Gender");
        myValidator.checkPicture(ivDp,"Select Image");
        myValidator.singleOptionsSelected(cbTerms,"Accept teams and conditions");
        myValidator.checkMinMax(tlDes,0,4,"Enter 4 characters");

    }

    private void doValidation() {
        myValidator.doValidation();

    }

    @Override
    public void onSuccessValidation() {
        showToast("Success");
    }

    @Override
    public void onValidationError(List<ValidationResult> validationResultList) {
        for(ValidationResult validationResult : validationResultList){
            if(validationResult.view instanceof RadioGroup){
                showToast(validationResult.errMsg);
            }

            if(validationResult.view instanceof CheckBox){
                showToast(validationResult.errMsg);
            }

            if(validationResult.view instanceof ImageView){
                showToast(validationResult.errMsg);
            }
        }

    }

    private void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
