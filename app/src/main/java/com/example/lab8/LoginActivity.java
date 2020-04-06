package com.example.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnSignin;
    TextView tvsignup;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mFirebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);

        tvsignup = findViewById(R.id.textView2);

        btnSignin = findViewById(R.id.button);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this,"you are logged in", Toast.LENGTH_SHORT).show();
                    Intent Int= new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(Int);
                }

                else{
                    Toast.makeText(LoginActivity.this,"Please login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email=email.getText().toString();
                String Password=password.getText().toString();
                if(Email.isEmpty()){
                    email.setError("please enter an email");
                    email.requestFocus();
                }

                else if(Password.isEmpty()){
                    password.setError("Please enter a password");
                    password.requestFocus();
                }

                else if(Email.isEmpty() && Password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }

                else if(!(Email.isEmpty() && Password.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Login Error, login again", Toast.LENGTH_SHORT).show();

                                } else{
                                    Intent intToHome= new Intent (LoginActivity.this, HomeActivity.class);
                                    startActivity(intToHome);
                                }
                        }
                    });

                }

                else {
                    Toast.makeText(LoginActivity.this,"Error Occured", Toast.LENGTH_SHORT).show();

                }

            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntSignup= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(IntSignup);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}