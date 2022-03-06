package com.example.roadsidevehicleasistance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Businessregister extends AppCompatActivity implements View.OnClickListener {
    private TextView BusinessOwnerRegister;
    private EditText name, email, password, confirmpassword, address;
    private Button register;
    private ProgressBar progressBar;
    private Button CreateBusiness;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessregister);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();
        register = (Button) findViewById(R.id.button7);
        register.setOnClickListener(this);

        name = (EditText) findViewById(R.id.editTextTextPersonName9);
        email = (EditText) findViewById(R.id.editTextTextPersonName10);
        password = (EditText) findViewById(R.id.editTextTextPassword5);
        confirmpassword = (EditText) findViewById(R.id.editTextTextPassword6);
        address = (EditText) findViewById(R.id.editTextTextPostalAddress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        CreateBusiness = findViewById(R.id.button9);
        CreateBusiness.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Businessregister.this, CreateBusiness.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button7:
                register();
                break;


        }
    }

    private void register() {

        final String sname = name.getText().toString().trim();
        final String semail = email.getText().toString().trim();
        String spwd = password.getText().toString().trim();
        final String saddr = address.getText().toString().trim();
        String scpwd = confirmpassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            email.setError("Please provide valid Email!");
            email.requestFocus();
            return;

        }
        if (password.length() < 6) {
            password.setError("Min Password length should be 6 characters!");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(semail, spwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Bregister bregister = new Bregister(sname, semail, saddr);

                            FirebaseDatabase.getInstance().getReference("Bregister")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(bregister).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Businessregister.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        //redirect to TechnicianLoginpage layout!
                                    } else {
                                        Toast.makeText(Businessregister.this, "Failed to register! Try Again!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });


                        } else {
                            Toast.makeText(Businessregister.this, "Failed to register! Try Again!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}