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

public class TechnicianLogin extends AppCompatActivity implements View.OnClickListener {

    private TextView login;
    private EditText email, password;
    private Button Login;
    private ProgressBar progressBar;

    private Button Register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_login);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        Register=(Button)findViewById(R.id.button5);
        Register.setOnClickListener(this);

        Login = (Button) findViewById(R.id.button8);
        Login.setOnClickListener(this);
        email = (EditText) findViewById(R.id.editTextTextPersonName3);
        password = (EditText) findViewById(R.id.editTextTextPassword3);
        progressBar=(ProgressBar)findViewById(R.id.progressBar4);
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button8:
                techLogin();
                break;

            case R.id.button5:
                Intent intent = new Intent(TechnicianLogin.this, Businessregister.class);
                startActivity(intent);

        }

    }

    private void techLogin() {

        String demail = email.getText().toString().trim();
        String dPassword = password.getText().toString().trim();

        if (demail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(demail).matches()) {
            email.setError("Please Enter a valid email");
            email.requestFocus();
            return;
        }

        if (dPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(demail, dPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(TechnicianLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(TechnicianLogin.this,CreateBusiness.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(TechnicianLogin.this, "Failed to Login", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}