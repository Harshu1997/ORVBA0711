package com.example.roadsidevehicleasistance;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_Home:
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, LoginPage.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final TextView textView8 = (TextView) findViewById(R.id.textView8);
        final TextView textViewfname = (TextView) findViewById(R.id.fname);
        final TextView textViewmail = (TextView) findViewById(R.id.mail);
        final TextView textViewadd = (TextView) findViewById(R.id.address3);
        final TextView textViewmob = (TextView) findViewById(R.id.mob);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {
                    String firstname = userprofile.firstname;
                    String email = userprofile.email;
                    String address = userprofile.address;
                    String phoneno = userprofile.phoneno;

                    textViewfname.setText(firstname);
                    textViewmail.setText(email);
                    textViewadd.setText(address);
                    textViewmob.setText(phoneno);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Profile.this, "Something wrong happened", Toast.LENGTH_LONG).show();

            }
        });


    }


    }



