package com.example.roadsidevehicleasistance;

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


public class Register extends AppCompatActivity implements View.OnClickListener {
    private TextView mRegister;
    private EditText mFirstName,mLastName,mEmail,mAddress,mPhoneno,mPassword,mConfirmPassword;
    private ProgressBar mProgressBar;
    private Button mCreate;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();
        mCreate=(Button)findViewById(R.id.button2);

        mCreate.setOnClickListener(this);
        mFirstName=(EditText)findViewById(R.id.FName);
        mLastName=(EditText)findViewById(R.id.LName);
        mEmail=(EditText)findViewById(R.id.Email);
        mAddress=(EditText)findViewById(R.id.Addr);
        mPhoneno=(EditText)findViewById(R.id.mobile);
        mPassword=(EditText)findViewById(R.id.Password);
        mConfirmPassword=(EditText)findViewById(R.id.CPassword);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);



    }


    @Override
    public void onClick(View view) {
     switch (view.getId()){
    case R.id.button2:
        signin();
        break;

}
    }

    private void signin() {
        final String email=mEmail.getText().toString().trim();
        final String firstname=mFirstName.getText().toString().trim();
        final String lastname=mLastName.getText().toString().trim();
        final String address=mAddress.getText().toString().trim();
        final String phoneno=mPhoneno.getText().toString().trim();
        String pwd=mPassword.getText().toString().trim();
        String cpwd=mConfirmPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Please provide valid Email!");
            mEmail.requestFocus();
            return;



        }
        if(mPassword.length()<6){
            mPassword.setError("Min Password length should be 6 characters!");
            mPassword.requestFocus();
            return;

        }
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pwd)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     User user=new User(firstname,lastname,email,address,phoneno);

                     FirebaseDatabase.getInstance().getReference("Users")
                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                             .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {

                             if(task.isSuccessful()){
                                 Toast.makeText(Register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                 mProgressBar.setVisibility(View.GONE);

                                 //redirect to loginpage layout!
                             }else{
                                 Toast.makeText(Register.this, "Failed to register! Try Again!", Toast.LENGTH_SHORT).show();
                                 mProgressBar.setVisibility(View.GONE);
                             }

                         }
                     });


                 }else{
                     Toast.makeText(Register.this, "Failed to register! Try Again!", Toast.LENGTH_SHORT).show();
                     mProgressBar.setVisibility(View.GONE);
            }
        }
    });
    }
}

