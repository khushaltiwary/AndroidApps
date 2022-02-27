package com.example.brainbooster2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVGKAdapter extends RecyclerView.Adapter<RecyclerVGKAdapter.MyViewHolder> {
    Context context;
    ArrayList<FirestoreUserGk> firestoreUserGkArrayList;


    public RecyclerVGKAdapter(Context context,ArrayList<FirestoreUserGk> firestoreUserGKArrayList) {
        this.context = context;
        this.firestoreUserGkArrayList = firestoreUserGKArrayList;
    }

    @NonNull
    @Override
    public RecyclerVGKAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.gk_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVGKAdapter.MyViewHolder holder, int position) {
        FirestoreUserGk firestoreUserGk = firestoreUserGkArrayList.get(position);

        holder.userName.setText(firestoreUserGk.userName);
        holder.userGkScore.setText(firestoreUserGk.userGkScore);
    }

    @Override
    public int getItemCount() {
        return firestoreUserGkArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userName,userGkScore;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameItem);
            userGkScore = itemView.findViewById(R.id.userGkScoreItem);
        }
    }
}