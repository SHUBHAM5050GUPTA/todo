package com.example.shubhamgupta.todo1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    AutoCompleteTextView emailTextView;
    AutoCompleteTextView passwordTextView;
    AutoCompleteTextView confirmTextView;
    Button registerButton;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        emailTextView= (AutoCompleteTextView) findViewById(R.id.user_emailId_register);
        passwordTextView= (AutoCompleteTextView) findViewById(R.id.user_password_register);
        confirmTextView= (AutoCompleteTextView) findViewById(R.id.confirm_password_register);
        registerButton= (Button) findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailTextView.getText().toString().trim();
                String password = passwordTextView.getText().toString().trim();
                String confirnPassword=confirmTextView.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confirnPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password entered don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("shubhhhjj",email+password);
                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.i("shubham", "onComplete: Failed=" + task.getException().getMessage());
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(Register.this, LogIn.class));
                                    finish();
                                }
                            }
                        });

            }
        });



    }
}
