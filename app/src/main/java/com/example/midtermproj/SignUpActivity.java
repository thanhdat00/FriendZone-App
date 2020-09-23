package com.example.midtermproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputEmail;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textInputUsername = findViewById(R.id.username_input_register);
        textInputEmail = findViewById(R.id.email_input_register);
        textInputPassword= findViewById(R.id.password_input_register);
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

    public void onClickRegister(View view) {
        if(!validateEmail() | !validatePassword() | !validateUsername()) {
            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("UserAccount");

        String userName = textInputUsername.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();
        String email = textInputEmail.getEditText().getText().toString();

        User newUser = new User(userName, email, password);

        reference.child(userName).setValue(newUser);

        this.finish();
    }
}