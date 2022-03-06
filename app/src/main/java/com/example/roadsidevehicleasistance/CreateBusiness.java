package com.example.roadsidevehicleasistance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateBusiness extends AppCompatActivity implements View.OnClickListener {

    private TextView CreateBusiness;
    private EditText BusinessName;
    private EditText MechanicName;
    private EditText Services;
    private EditText Available;
    private EditText Address;
    private EditText Area;
    private EditText Mobile;
     private Button Save;

    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        db=FirebaseFirestore.getInstance();

        CreateBusiness=findViewById(R.id.textView5);
        BusinessName=findViewById(R.id.editTextTextPersonName5);
        MechanicName=findViewById(R.id.editTextTextPersonName11);
        Services=findViewById(R.id.editTextTextPersonName12);
        Available=findViewById(R.id.editTextTextPersonName13);
        Address=findViewById(R.id.editTextTextPersonName14);
        Area=findViewById(R.id.editTextTextPersonName15);
        Mobile=findViewById(R.id.editTextTextPersonName16);
        Save= (Button) findViewById(R.id.button10);
        Save.setOnClickListener(this);
    }
    private boolean validateInputs(String Businessname,String Mechanicname,String services,String available,String address,String area,String mobile){
        if(Businessname.isEmpty()){
            BusinessName.setError("Name required");
            BusinessName.requestFocus();
            return true;
        }
        if(Mechanicname.isEmpty()){
            MechanicName.setError("Name required");
            MechanicName.requestFocus();
            return true;
        }
   return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button10:
                createBusiness();
                break;
        }
    }
private void createBusiness() {
            String Businessname = BusinessName.getText().toString().trim();
            String Mechanicname = MechanicName.getText().toString().trim();
            String services = Services.getText().toString().trim();
            String available = Available.getText().toString().trim();
            String address = Address.getText().toString().trim();
            String area = Area.getText().toString().trim();
            String mobile = Mobile.getText().toString().trim();

            if (!validateInputs(Businessname, Mechanicname, services, available, address, area, mobile)) {

                CollectionReference dbBusiness = db.collection("business");

                Business business = new Business(
                        Businessname,
                        Mechanicname,
                        services,
                        available, address,
                        area,
                        Integer.parseInt(mobile)
                );
                dbBusiness.add(business)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(CreateBusiness.this, "Saved", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateBusiness.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }


