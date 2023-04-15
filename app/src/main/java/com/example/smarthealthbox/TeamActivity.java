package com.example.smarthealthbox;

import android.content.Intent;
import android.os.Bundle;

import com.example.smarthealthbox.databinding.ActivityTeamBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class TeamActivity extends AppCompatActivity {

private ActivityTeamBinding binding;

FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityTeamBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

     fab=findViewById(R.id.fab);

     fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(TeamActivity.this,HomeActivity.class));
             finish();
         }
     });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(TeamActivity.this,HomeActivity.class));
        finish();
    }
}