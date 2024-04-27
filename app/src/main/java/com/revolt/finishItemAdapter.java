package com.revolt;

import static androidx.core.content.ContextCompat.*;
import static com.revolt.R.*;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class finishItemAdapter extends RecyclerView.Adapter<finishItemAdapter.MyViewHolder> {


    Context context;
    ArrayList<finishItems> list;
//    SelectListener listener;

    public finishItemAdapter(Context context, ArrayList<finishItems> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout.item_quest,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        finishItems finItem = list.get(position);

        if (finItem != null) {
            holder.questions.setText(finItem.getQuestion());
            holder.correctAns.setText(finItem.getCorrectAnswers());
            holder.selectedAns.setText(finItem.getSelectedAnswer());
            holder.remarks.setText(finItem.getRemarks());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView questions, correctAns, selectedAns, remarks;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            questions = itemView.findViewById(id.itemQuestion);
            correctAns = itemView.findViewById(id.correctAns);
            selectedAns = itemView.findViewById(id.selectedAnswer);
            remarks = itemView.findViewById(id.itemRemarks);

        }
    }




}
