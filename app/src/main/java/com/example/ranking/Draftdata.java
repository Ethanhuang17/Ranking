package com.example.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Draftdata extends AppCompatActivity {
    private String[] legends = new String[] {"AXE", "ANTIMAGE", "VOID", "TINKER", "WINDRANGER"};

    private String selection;

    private String email;

    private Double kills;

    private Double deads;

    private Double assists;

    private Double kdas;

    private DatabaseReference mDatabase;

    private DatabaseReference mRef;

    private List<ArrayList> getarray;

    private ArrayList<ArrayList> axe = new ArrayList<ArrayList>(){};

    private ArrayList<ArrayList> tinker = new ArrayList<ArrayList>(){};

    private ArrayList<ArrayList> antimage = new ArrayList<ArrayList>(){};

    private ArrayList<ArrayList>  vod = new ArrayList<ArrayList>(){};

    private ArrayList<ArrayList> windranger = new ArrayList<ArrayList>(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draftdata);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Spinner legend = findViewById(R.id.spinner);
        EditText kill = findViewById(R.id.kill);
        EditText dead = findViewById(R.id.dead);
        EditText assist = findViewById(R.id.assist);
        TextView kda = findViewById(R.id.kda);
        Button upload = findViewById(R.id.upload);
        Button back = findViewById(R.id.back);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        getarray = new ArrayList<>();
        ArrayAdapter x = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legends);
        x.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        legend.setAdapter(x);
        legend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view,
                                       final int position, final long id) {
                selection = parent.getItemAtPosition(position).toString();
                // Called when the user selects a different item in the dropdown
                // The position parameter is the selected index
                // The other parameters can be ignored
            }
            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                // Called when the selection becomes empty
                // Not relevant to the MP - can be left blank
            }
        });

        Intent mainintent = new Intent(this, NewMainActivity.class);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainintent);
                finish();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringkill = kill.getText().toString();
                kills = Double.parseDouble(stringkill);
                String stringdead = dead.getText().toString();
                deads = Double.parseDouble(stringdead);
                String stringassist = assist.getText().toString();
                assists = Double.parseDouble(stringassist);
                kdas = ((kills + assists) / deads);
                kda.setText(kdas.toString());
                uploadclicked();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                axe.clear();
                tinker.clear();
                windranger.clear();
                antimage.clear();
                vod.clear();
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





    private void uploadclicked() {
        if (selection.equals("AXE")) {
            mRef = mDatabase.child(selection);
            ArrayList<Double> element = new ArrayList<Double>();
            element.add(0, 1000.0);
            element.add(1, kills);
            element.add(2, deads);
            element.add(3, assists);
            element.add(4, kdas);
            axe.add(element);
            mRef.setValue(axe);
        } else if (selection.equals("TINKER")) {
            mRef = mDatabase.child(selection);
            ArrayList<Double> element = new ArrayList<Double>();
            element.add(0, 1001.0);
            element.add(1, kills);
            element.add(2, deads);
            element.add(3, assists);
            element.add(4, kdas);
            tinker.add(element);
            mRef.setValue(tinker);
        } else if (selection.equals("ANTIMAGE")) {
            mRef = mDatabase.child(selection);
            ArrayList<Double> element = new ArrayList<Double>();
            element.add(0, 1002.0);
            element.add(1, kills);
            element.add(2, deads);
            element.add(3, assists);
            element.add(4, kdas);
            antimage.add(element);
            mRef.setValue(antimage);
        } else if (selection.equals("WINDRANGER")) {
            mRef = mDatabase.child(selection);
            ArrayList<Double> element = new ArrayList<Double>();
            element.add(0, 1003.0);
            element.add(1, kills);
            element.add(2, deads);
            element.add(3, assists);
            element.add(4, kdas);
            windranger.add(element);
            mRef.setValue(windranger);
        } else if (selection.equals("VOID")) {
            mRef = mDatabase.child(selection);
            ArrayList<Double> element = new ArrayList<Double>();
            element.add(0, 1004.0);
            element.add(1, kills);
            element.add(2, deads);
            element.add(3, assists);
            element.add(4, kdas);
            vod.add(element);
            mRef.setValue(vod);
        }
    }
}