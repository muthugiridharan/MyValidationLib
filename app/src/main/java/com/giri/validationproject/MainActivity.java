package com.giri.validationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.giri.myvalidationlib.model.ValidationResult;
import com.giri.myvalidationlib.validator.MyValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyValidator.MyValidatorListener{

    private  final String TAG = MainActivity.class.getSimpleName();
    private TextInputLayout tlFirstName,tlLastName,tlUserName,tlEmail,tlPassword,tlConfirmPassword,tlDateOfBirth;
    private RadioGroup rgGender;
    private MaterialCheckBox cbTerms;
    private MyValidator myValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialButton btnSubmit = findViewById(R.id.btnSubmit);
        tlFirstName = findViewById(R.id.tlFirstName);
        tlLastName =findViewById(R.id.tlLastName);
        tlUserName = findViewById(R.id.tlUserName);
        tlPassword = findViewById(R.id.tlPassword);
        tlEmail = findViewById(R.id.tlEmail);
        rgGender = findViewById(R.id.rgGender);
        cbTerms = findViewById(R.id.cbTerms);
        tlConfirmPassword = findViewById(R.id.tlConfirmPassword);
        tlDateOfBirth = findViewById(R.id.tlDateOfBirth);
        Activity activity = this;
        myValidator = new MyValidator(activity);
        myValidator.setListner(this);
        myValidator.isSetErrorMessage(true);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doValidation();
            }
        });

    }

    private void doValidation() {
        myValidator.checkEmail(tlEmail,"Enter valid email");
        myValidator.checkName(tlFirstName,"Enter FirstName");
        myValidator.checkName(tlLastName,"Enter Last Name");
        myValidator.notEmpty(tlUserName,"Enter User Name");
        myValidator.checkPassword(tlPassword,"medium_password","Enter valid password");
        myValidator.checkConfirmPassword(tlPassword,tlConfirmPassword,"Password mismatch");
        myValidator.checkDateOfBirth(tlDateOfBirth,"dd-MM-YYYY",18,"Age must be above 18");
        myValidator.singleOptionsSelected(rgGender,"Select Gender");
        myValidator.singleOptionsSelected(cbTerms,"Accept teams and conditions");
        myValidator.doValidation(true);

    }

    @Override
    public void onSuccessValidation() {
        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationError(List<ValidationResult> validationResultList) {

        for(ValidationResult validationResult : validationResultList){
            if(validationResult.view instanceof RadioGroup){
                Toast.makeText(getApplicationContext(),validationResult.errMsg,Toast.LENGTH_LONG).show();
            }

            if(validationResult.view instanceof CheckBox){
                Toast.makeText(getApplicationContext(),validationResult.errMsg,Toast.LENGTH_LONG).show();
            }
        }

    }
}
