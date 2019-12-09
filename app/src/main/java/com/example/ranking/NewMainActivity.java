package com.example.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewMainActivity extends AppCompatActivity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent previousintent = getIntent();
        email = previousintent.getStringExtra("email");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        Button forinputdata = findViewById(R.id.data);
        Button forstart = findViewById(R.id.start);
        forinputdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendraftdata();
            }
        });
        forstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAnalysis();
            }
        });
    }
    public void opendraftdata() {
        Intent intent = new Intent(this, Draftdata.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
    public void openAnalysis() {
        Intent analysisintent = new Intent(this, RankingActivity.class);
        startActivity(analysisintent);
    }
}
