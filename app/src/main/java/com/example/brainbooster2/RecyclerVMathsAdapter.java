package com.example.brainbooster2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVMathsAdapter extends RecyclerView.Adapter<RecyclerVMathsAdapter.MyViewHolder> {

    Context context;
    ArrayList<FirestoreUser> firestoreUserArrayList;


    public RecyclerVMathsAdapter(Context context, ArrayList<FirestoreUser> firestoreUserArrayList) {
        this.context = context;
        this.firestoreUserArrayList = firestoreUserArrayList;
    }

    @NonNull
    @Override
    public RecyclerVMathsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVMathsAdapter.MyViewHolder holder, int position) {
        FirestoreUser firestoreUser = firestoreUserArrayList.get(position);

        holder.userName.setText(firestoreUser.userName);
        holder.userMathsScore.setText(firestoreUser.userMathsScore);
    }

    @Override
    public int getItemCount() {
        return firestoreUserArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName,userMathsScore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameItem);
            userMathsScore = itemView.findViewById(R.id.userMathsScoreItem);
        }
    }
}
