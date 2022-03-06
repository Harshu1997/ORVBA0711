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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    private TextView LoginPage;
    private EditText emailid, password;
    private Button Submit;
private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private Button Fpwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);



        Fpwd=(Button) findViewById(R.id.button13);
        Fpwd.setOnClickListener(this);
        Submit = (Button) findViewById(R.id.button4);
        Submit.setOnClickListener(this);
        emailid = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pwd);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button13:
                startActivity(new Intent(LoginPage.this,ResetPassword.class));
            case R.id.button4:
                userLogin();
                break;


        }


    }


    private void userLogin() {

        String semail = emailid.getText().toString().trim();
        String sPassword = password.getText().toString().trim();

        if (semail.isEmpty()) {
            emailid.setError("Email is required");
            emailid.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            emailid.setError("Please Enter a valid email");
            emailid.requestFocus();
            return;
        }

        if (sPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(semail, sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginPage.this,HomePage.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(LoginPage.this, "Failed to Login", Toast.LENGTH_LONG).show();
                      progressBar.setVisibility(View.GONE);
                }

                }

        });

}
}







