package com.example.navdrawer2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText memail, mpassword;
    private Button mlogin,mregistration;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    //make sure user is logged in to move forward
                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        };

        memail = (EditText) findViewById(R.id.email);
        mpassword= (EditText) findViewById(R.id.Password);

        mlogin = (Button) findViewById(R.id.Login);
        mregistration= (Button) findViewById(R.id.Registration);

        mregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email =memail.getText().toString();
                final String password =mpassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            //when login fails
                            Toast.makeText(LoginActivity.this,"Sign up error",Toast.LENGTH_SHORT).show();
                        }else{
                            //when it is in authentication database in firebase
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
                            current_user_db.setValue(true);
                        }
                    }
                });

            }
        });
        //Login Button
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email =memail.getText().toString();
                final String password =mpassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            //when login fails
                            Toast.makeText(LoginActivity.this,"Login error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        //Whenever the activity is called
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        //When you leave
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}