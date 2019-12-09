package com.example.ranking;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private ArrayList<ArrayList<Double>> axe = new ArrayList<ArrayList<Double>>(){};

    private ArrayList<ArrayList<Double>> tinker = new ArrayList<ArrayList<Double>>(){};

    private ArrayList<ArrayList<Double>> antimage = new ArrayList<ArrayList<Double>>(){};

    private ArrayList<ArrayList<Double>>  vod = new ArrayList<ArrayList<Double>>(){};

    private ArrayList<ArrayList<Double>> windranger = new ArrayList<ArrayList<Double>>(){};

    private Map<Double, String> kda = new HashMap<Double, String>(){};

    private ArrayList<Double> array = new ArrayList<Double>(){};

    TextView first;
    TextView second;
    TextView third;
    TextView fourth;
    TextView fifth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button get = findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startget();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Object x =  data.getValue();
                    if (x instanceof ArrayList) {
                        ArrayList<ArrayList<Double>> y = (ArrayList<ArrayList<Double>>) x;
                        if (data.getKey().equals("AXE")) {
                            axe.addAll(y);
                        }
                        if (data.getKey().equals("TINKER")) {
                            tinker.addAll(y);
                        }
                        if (data.getKey().equals("WINDRANGER")) {
                            windranger.addAll(y);
                        }
                        if (data.getKey().equals("VOID")) {
                            vod.addAll(y);
                        }
                        if (data.getKey().equals("ANTIMAGE")) {
                            antimage.addAll(y);
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double getaverage(ArrayList<ArrayList<Double>> x) {
        double total = 0.0;
        int count = 0;
        for (ArrayList<Double> each : x) {
            total = each.get(4) + total + 0.0001;
            count = count + 1;
        }
        if (count != 0) {
            return (total / count);
        }
        return 0.0;
    }

    private void sort(ArrayList<Double> x) {
        if (x == null) {
            return;
        }
        if (x.size() == 0) {
            return;
        }
        for (int i = 1; i < x.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (x.get(j) > (x.get(j - 1))) {
                    Double t = x.get(j);
                    Double t2 = x.get(j - 1);
                    x.set(j, t2);
                    x.set(j - 1, t);
                }
            }
        }
    }

    private void startget() {
        array.clear();
        kda.clear();
        kda.put(getaverage(axe), "AXE");
        kda.put(getaverage(tinker), "TINKER");
        kda.put(getaverage(antimage), "ANTIMAGE");
        kda.put(getaverage(vod), "VOID");
        kda.put(getaverage(windranger), "WINDRANGER");
        array.add(getaverage(axe));
        array.add(getaverage(tinker));
        array.add(getaverage(vod));
        array.add(getaverage(antimage));
        array.add(getaverage(windranger));
        sort(array);
        first.setText(kda.get(array.get(0)) + " : " + (array.get(0)).toString());
        second.setText(kda.get(array.get(1)) + " : " + (array.get(1)).toString());
        third.setText(kda.get(array.get(2)) + " : " + (array.get(2)).toString());
        fourth.setText(kda.get(array.get(3)) + " : " + (array.get(3)).toString());
        fifth.setText(kda.get(array.get(4)) + " : " + (array.get(4)).toString());
    }
    public static Double roundDown5(Double number) {
        double real = (double) number;
        return ((long)(real * 1e5)) / 1e5;
    }

}
