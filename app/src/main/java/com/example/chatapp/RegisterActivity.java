package com.example.chatapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.base.Base;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends Base {

    private EditText mUserName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private Button mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
                registerUser();
            }
        });

    }


    FirebaseAuth mAuth;
    private void registerUser() {
        mAuth = FirebaseAuth.getInstance();
        String userNameText = mUserName.getText().toString();
        String EmailText = mEmail.getText().toString();
        String PasswordText = mPassword.getText().toString();
        showProgressDialog(getString(R.string.Loading));
        mAuth.createUserWithEmailAndPassword(EmailText, PasswordText).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showMessage("Registration Successful","OK");
                        }else{
                            showMessage(task.getException().getLocalizedMessage(),"OK");
                        }

                    }
                }
        );


    }
        private boolean validateForm () {
            boolean isValid = true;
            String userNameText = mUserName.getText().toString();
            String EmailText = mEmail.getText().toString();
            String PasswordText = mPassword.getText().toString();
            String PasswordConfirmText = mPasswordConfirm.getEditableText().toString();
            if (userNameText.trim().isEmpty()) {
                mUserName.setError("required");
            } else {
                mUserName.setError(null);
            }
            if (EmailText.trim().isEmpty()) {
                mEmail.setError("required");
                isValid = false;
            } else if (!isValidEmail()) {
                mEmail.setError("please enter valid email");
            } else {
                mEmail.setError(null);
            }
            if (PasswordText.trim().isEmpty()) {
                mPassword.setError("required");
                isValid = false;
            } else if (PasswordText.length() < 6) {
                isValid = false;
                mPassword.setError("password should be more than 5 chars");

            } else {
                mPassword.setError(null);
            }

            if (PasswordConfirmText.trim().isEmpty()) {
                mPasswordConfirm.setError("required");
                isValid = false;
            } else {
                mPasswordConfirm.setError(null);
            }

            if (!PasswordConfirmText.equals(PasswordText)) {
                mPasswordConfirm.setError("password doesn't match");
                mPassword.setError("password doesn't match");
                isValid = false;
            } else {
                mPasswordConfirm.setError(null);
                mPassword.setError(null);
            }

            return isValid;

        }

        private boolean isValidEmail () {
            return true;
        }

        private void initViews () {
            mUserName = findViewById(R.id.user_name);
            mEmail = findViewById(R.id.Email);
            mPassword = findViewById(R.id.password);
            mPasswordConfirm = findViewById(R.id.password_confirm);
            mRegister = findViewById(R.id.register);
        }
    }

