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

public class AdapterHard extends RecyclerView.Adapter<AdapterHard.MyViewHolder>{

    Context context;
    ArrayList<UserNew> list;

//    int dataScore;

    public AdapterHard(Context context, ArrayList<UserNew> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public AdapterHard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item3,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHard.MyViewHolder holder, int position) {

        UserNew usernew = list.get(position);

        holder.rank.setText(String.valueOf(position + 1));

        if (usernew != null) {
            holder.name.setText(usernew.getName());
            String substringToRemove = "@g.batstate-u.edu.ph";
            String modCode = usernew.getEmail().replace(substringToRemove, "");
            holder.email.setText(modCode);
//            holder.score.setText(String.valueOf(usernew.getScore()));
            holder.score.setText(String.valueOf(usernew.getScoreHard()));
            Picasso.get().load(usernew.getProfile()).into(holder.ProfilePicture);
        }

    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(), 3);
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
