package com.example.midtermproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputUsername = findViewById(R.id.username_input);
        textInputPassword= findViewById(R.id.password_input);
    }

    private boolean validateEmail() {
        String emailInput = textInputUsername.getEditText().getText().toString().trim();
        if (emailInput.isEmpty())
        {
            textInputUsername.setError("Field cannot be empty");
            return false;
        } else {
            textInputUsername.setError(null);
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


    public void onClickLogin(View view) {
        if(!validateEmail() | !validatePassword()) {
            return;
        }
        String input = "Email: " + textInputUsername.getEditText().getText().toString();
        input+="\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();

        Toast.makeText(this,input, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        this.startActivity(intent);
    }

    public void onClickCreateAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}