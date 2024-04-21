package com.revolt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapterHistory extends RecyclerView.Adapter<MyAdapterHistory.MyViewHolder> {
    Context context;
    ArrayList<History> list;

    public MyAdapterHistory(Context context, ArrayList<History> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        History his = list.get(position);

        if (his != null) {
            holder.Date.setText(his.getDate());
            holder.Difficulty.setText(his.getDifficulty());
            holder.Time.setText(his.getTime());
            holder.Topic.setText(String.valueOf(his.getTopic()));
            holder.score.setText(String.valueOf(his.getScore()));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Date,Difficulty,Time,Topic,score;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.DateVal);
            Difficulty = itemView.findViewById(R.id.DiffVal);
            Time = itemView.findViewById(R.id.TimeVal);
            Topic = itemView.findViewById(R.id.TopicVal);
            score = itemView.findViewById(R.id.ScoreVal);

            if (Date == null || Difficulty == null || Time == null || Topic == null || score == null) {
                Log.e("MyAdapter", "One or more views is null");
            }

        }
    }

}
