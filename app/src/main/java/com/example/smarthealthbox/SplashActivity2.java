package com.example.smarthealthbox;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity2 extends AppCompatActivity {

    private static int Splash=5000;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Data");
        tv = findViewById(R.id.textViewWelcome);
        iv= findViewById(R.id.imageView2);
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot dataSnapshot=task.getResult();
                    String name="Welcome "+String.valueOf(dataSnapshot.child("name").getValue());
                    Animation animation= AnimationUtils.loadAnimation(SplashActivity2.this,R.anim.anim);
                    tv.setText(name);
                    iv.setImageResource(R.drawable.logonobg);
                    tv.startAnimation(animation);
                    iv.startAnimation(animation);
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity2.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },Splash);


    }
}