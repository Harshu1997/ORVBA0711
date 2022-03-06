package com.example.roadsidevehicleasistance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class myadapter extends FirestoreRecyclerAdapter<Business,myadapter.BusinessViewHolder> {

    public myadapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
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

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);

        return new BusinessViewHolder(view);

    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {

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

        }
    }
}

