package com.revolt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserProfile extends AppCompatActivity implements SelectListener{

    DatabaseReference dbRef;

    RecyclerView recyclerViewHis;
    MyAdapterHistory myAdapterHis;
    ArrayList<History> listHis;

    List<Integer> values = new ArrayList<>();
    List<Integer> valuesM = new ArrayList<>();
    List<Integer> valuesH = new ArrayList<>();
    TextView NameOfUser,idSrCode;

    ImageView UserProPicture;
    Button back;
    BarChart barchart, barchart2, barchart3;

    String idProfile;

    ValueEventListener getProfile,getHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        idProfile = intent.getStringExtra("idprofile");
        NameOfUser = findViewById(R.id.NameOfUser);
        idSrCode = findViewById(R.id.idSrCode);
        UserProPicture = findViewById(R.id.UserProPicture);
        ImageView home = findViewById(R.id.goHome);

        assert idProfile != null;
        dbRef = FirebaseDatabase.getInstance().getReference("users").child(idProfile);

        getProfile = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Assuming you have the corresponding fields in your layout
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String linkPic = snapshot.child("profile").getValue(String.class);
                    String substringToRemove = "@g.batstate-u.edu.ph";
                    assert email != null;
                    String SrCode = email.replace(substringToRemove, "");

                    // Set the values to the TextViews
                    NameOfUser.setText(name);
                    idSrCode.setText(SrCode);
                    Picasso.get().load(linkPic).into(UserProPicture);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dbRef.addValueEventListener(getProfile);


        recyclerViewHis = findViewById(R.id.userListHistory);
        recyclerViewHis.setHasFixedSize(true);
        recyclerViewHis.setLayoutManager(new LinearLayoutManager(this));
        listHis = new ArrayList<>();
        myAdapterHis = new MyAdapterHistory(this, listHis, this);
        recyclerViewHis.setAdapter(myAdapterHis);

        getHistory = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listHis.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String Id = dataSnapshot.getKey();
                    String date = dataSnapshot.child("Date").getValue(String.class);
                    String diff = dataSnapshot.child("Difficulty").getValue(String.class);
                    String time = dataSnapshot.child("Time").getValue(String.class);
                    String topic = dataSnapshot.child("Topic").getValue(String.class);
                    int score = dataSnapshot.child("score").getValue(Integer.class);


                    listHis.add(new History(date,diff,time,topic,score,Id));

                    switch (Objects.requireNonNull(diff)) {
                        case "Easy":
                            values.add(score);
                            break;
                        case "Medium":
                            valuesM.add(score);
                            break;
                        case "Hard":
                            valuesH.add(score);
                            break;
                    }

                }


                BarChart();
                BarChartMedium();
                BarChartHard();

                // Check the size of the list after adding data
                Log.d("TAG", "Size of listHis: " + listHis.size());

                myAdapterHis.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dbRef.child("BattleMode").addValueEventListener(getHistory);





        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.removeEventListener(getProfile);
                dbRef.child("BattleMode").removeEventListener(getHistory);

                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        // Call finish to close the current activity when the back button is pressed
        super.onBackPressed();
        dbRef.removeEventListener(getProfile);
        dbRef.child("BattleMode").removeEventListener(getHistory);
        Toast.makeText(UserProfile.this, "Back", Toast.LENGTH_LONG).show();
        finish();
    }




    public void BarChart(){
        barchart = findViewById(R.id.barchart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0;i<values.size();i++){
            BarEntry barEntry = new BarEntry(i,values.get(i));
            barEntries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Tests");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        barchart.setData(new BarData(barDataSet));
        barchart.animateY(300);
        barchart.getDescription().setText(" ");
        barchart.getDescription().setTextColor(Color.BLUE);


        XAxis xAxis = barchart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value + 1);
            }
        });

        YAxis yAxis = barchart.getAxisLeft();
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return "Score - " + String.valueOf((int) value);
            }
        });


        yAxis.setGranularity(1f);
        yAxis.setGranularityEnabled(true);

        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barchart.getAxisRight().setEnabled(false);
        barchart.invalidate();

    }



    public void BarChartMedium(){
        barchart2 = findViewById(R.id.barchartMedium);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0;i<valuesM.size();i++){
            BarEntry barEntry = new BarEntry(i,valuesM.get(i));
            barEntries.add(barEntry);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Tests");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);



        barchart2.setData(new BarData(barDataSet));
        barchart2.animateY(300);
        barchart2.getDescription().setText(" ");
        barchart2.getDescription().setTextColor(Color.BLUE);

        XAxis xAxis = barchart2.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value + 1);
            }
        });

        YAxis yAxis = barchart2.getAxisLeft();
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return "Score - " +String.valueOf((int) value);
            }
        });

        yAxis.setGranularity(1f);
        yAxis.setGranularityEnabled(true);

        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barchart2.getAxisRight().setEnabled(false);
        barchart2.invalidate();


    }

    public void BarChartHard(){
        barchart3 = findViewById(R.id.barchartHard);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0;i<valuesH.size();i++){
            BarEntry barEntry = new BarEntry(i,valuesH.get(i));
            barEntries.add(barEntry);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Tests");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        barchart3.setData(new BarData(barDataSet));
        barchart3.animateY(300);
        barchart3.getDescription().setText(" ");
        barchart3.getDescription().setTextColor(Color.BLUE);


        XAxis xAxis = barchart3.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value + 1);
            }
        });

        YAxis yAxis = barchart3.getAxisLeft();
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return "Score - " +String.valueOf((int) value);
            }
        });

        yAxis.setGranularity(1f);
        yAxis.setGranularityEnabled(true);

        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barchart3.getAxisRight().setEnabled(false);
        barchart3.invalidate();



    }

    @Override
    public void onItemClicked(History history) {
        Toast.makeText(UserProfile.this, history.getTopic(), Toast.LENGTH_LONG).show();
        Intent checkItem = new Intent(getApplicationContext(), CheckItemQuiz.class);
        checkItem.putExtra("idprofile", idProfile);
        checkItem.putExtra("TimeBattleMode", history.getId());
        startActivity(checkItem);
    }
}