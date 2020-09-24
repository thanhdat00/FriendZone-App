package com.example.midtermproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    public void logInUser(View view)
    {
        if(!validateEmail() | !validatePassword()) {
            return;
        }
        else {
            isUser();
        }

    }

    private void isUser() {
        final String userEnteredUserName = textInputUsername.getEditText().getText().toString().trim();
        final String userEnteredPassword = textInputPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserAccount");
        Query checkUser = reference.orderByChild("userName")
                        .equalTo(userEnteredUserName);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String passwordFromDB =
                            dataSnapshot.child(userEnteredUserName).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "NO USER EXISTS", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickLogin(View view) {
        logInUser(view);
    }

    public void onClickCreateAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}