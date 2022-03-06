package com.example.roadsidevehicleasistance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText Uemail;
    private Button Rpwd;
private ProgressBar Pbar2;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Uemail=(EditText)findViewById(R.id.editTextTextPersonName);
        Rpwd=(Button)findViewById(R.id.button15);
        firebaseAuth = FirebaseAuth.getInstance();
        Pbar2=findViewById(R.id.pBar);

        Rpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {

        String email=Uemail.getText().toString().trim();
        if(email.isEmpty()){
            Uemail.setError("Email is required");
            Uemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Uemail.setError("Please provide valid email");
            Uemail.requestFocus();
            return;
        }
        Pbar2.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPassword.this,
                                    "Check your email to reset your password"
                                    , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPassword.this,LoginPage.class));
                        }else {
                            Toast.makeText(ResetPassword.this,"Try again!",
                                    Toast.LENGTH_SHORT).show();
                            Pbar2.setVisibility(View.GONE);
                        }
                    }
                });
    }


}
