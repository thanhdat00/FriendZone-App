package com.example.midtermproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textInputUsername = findViewById(R.id.username_input);
        textInputEmail = findViewById(R.id.email_input);
        textInputPassword= findViewById(R.id.password_input);
    }

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty())
        {
            textInputUsername.setError("Field cannot be empty");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty())
        {
            textInputEmail.setError("Field cannot be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty())
        {
            textInputPassword.setError("Field cannot be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }



    public void onClickCreateAccount(View view) {
        if(!validateEmail() | !validatePassword() | !validateUsername()) {
            return;
        }
        String input = "Username: " + textInputUsername.getEditText().getText().toString();
        input+="\n";
        input += "Email: " + textInputEmail.getEditText().getText().toString();
        input+="\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();

        Toast.makeText(this,input, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}