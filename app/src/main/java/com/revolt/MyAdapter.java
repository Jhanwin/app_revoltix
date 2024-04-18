package com.revolt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Users> list;

    public MyAdapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Users user = list.get(position);

        holder.rank.setText(String.valueOf(position + 1));

        if (user != null) {
            holder.name.setText(user.getName());
            String substringToRemove = "@g.batstate-u.edu.ph";
            String modCode = user.getEmail().replace(substringToRemove, "");
            holder.email.setText(modCode);
            holder.score.setText(String.valueOf(user.getScore()));
            Picasso.get().load(user.getProfile()).into(holder.ProfilePicture);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,email,score,rank;
        ImageView ProfilePicture;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtName);
            email = itemView.findViewById(R.id.txtEmail);
            score = itemView.findViewById(R.id.txtScore);
            ProfilePicture = itemView.findViewById(R.id.ProfilePicture);
            rank = itemView.findViewById(R.id.RankVal);

        }
    }

}
