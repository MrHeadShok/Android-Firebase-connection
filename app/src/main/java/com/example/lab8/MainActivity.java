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

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button btnSignup;
    TextView tvsignin;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);

        tvsignin=findViewById(R.id.textView2);

        btnSignup=findViewById(R.id.button);

        btnSignup.setOnClickListener(new View.OnClickListener(){

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
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }

                else if(!(Email.isEmpty() && Password.isEmpty())){
                   // mFirebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                     mFirebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                Toast.makeText(MainActivity.this,"Executed", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Task non successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else {
                    Toast.makeText(MainActivity.this,"Error Occured", Toast.LENGTH_SHORT).show();

                }


            }
        });

        tvsignin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent Int =new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Int);
            }
        });
    }
}
