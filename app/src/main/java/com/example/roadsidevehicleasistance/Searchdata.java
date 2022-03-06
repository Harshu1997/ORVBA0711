package com.example.roadsidevehicleasistance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Searchdata extends AppCompatActivity{

    private static final int Request_call=1;

    private FirebaseFirestore firebaseFirestore;

    private RecyclerView mrecview;
    private FirestoreRecyclerOptions options;
    FirestoreRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdata);


        mrecview = findViewById(R.id.recview);


        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("business");

        FirestoreRecyclerOptions<Business> options = new FirestoreRecyclerOptions.Builder<Business>()
                .setQuery(query, Business.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Business, BusinessViewHolder>(options) {
            @NonNull
            @Override
            public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);

                return new BusinessViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BusinessViewHolder holder, int position, @NonNull Business model) {

                holder.bname.setText(model.getBusinessname());
                holder.mname.setText(model.getMechanicname());
                holder.mservice.setText(model.getServices());
                holder.mavailable.setText(model.getAvailable());
                holder.maddress.setText(model.getAddress());
                holder.marea.setText(model.getArea());
                holder.mmobile.setText(model.getMobile() + "");


            }
        };


        mrecview.setHasFixedSize(true);
        mrecview.setLayoutManager(new LinearLayoutManager(this));
        mrecview.setAdapter(adapter);

    }




    private class BusinessViewHolder extends RecyclerView.ViewHolder {

        private final TextView bname;
        private final TextView mname;
        private final TextView mservice;
        private final TextView mavailable;
        private final TextView maddress;
        private final TextView marea;
        private final TextView mmobile;


        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);

            bname = itemView.findViewById(R.id.bname);
            mname = itemView.findViewById(R.id.mechname);
            mservice = itemView.findViewById(R.id.service);
            mavailable = itemView.findViewById(R.id.available);
            maddress = itemView.findViewById(R.id.addr);
            marea = itemView.findViewById(R.id.area);
            mmobile = itemView.findViewById(R.id.phone);
            mmobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String phone = mmobile.getText().toString();
                    if (phone.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter Number"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        String s = "tel:" + phone;
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(s));
                        startActivity(intent);
                    }
                }
            });
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                processsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s){


        FirestoreRecyclerOptions<Business> options = new FirestoreRecyclerOptions.Builder<Business>()
                .setQuery(FirebaseFirestore.getInstance().collection("business").orderBy("area").startAt(s).endAt(s+"\uf8ff"), Business.class)
                .build();

        


        adapter=new myadapter(options);
        adapter.startListening();
        mrecview.setAdapter(adapter);
    }

}

