package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.math.BigInteger;

public class MainActivity2 extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView tv1,tv2,tv3;
    private ImageView iv;
    Button b1;
    String progress;
    String bp,glucose,spo2,pulserate,height,weight,smoke,bpdown;
    String bp1,glucose1,spo21,pulserate1,height1,weight1,smoke1,bpdown1;
    String bmi,bmi1;
    float h,w;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RelativeLayout relativeLayout = findViewById(R.id.rl1);
        RelativeLayout relativeLayout1 = findViewById(R.id.rl2);
        RelativeLayout relativeLayout2 = findViewById(R.id.rl3);
        RelativeLayout relativeLayout3 = findViewById(R.id.rl4);
        RelativeLayout relativeLayout4 = findViewById(R.id.rl5);
        RelativeLayout relativeLayout5 = findViewById(R.id.rl6);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Health");
        reference1 = firebaseDatabase.getReference("Information");

        TextView progressText = findViewById(R.id.spo2);
        TextView progressText1 = findViewById(R.id.glucose);
        TextView progressText2 = findViewById(R.id.bmi);
        TextView progressText3 = findViewById(R.id.bp);
        TextView progressText4 = findViewById(R.id.pulserate);
        TextView progressText5 = findViewById(R.id.smoking);

        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    bp = String.valueOf(dataSnapshot.child("blood pressure").getValue());
                    glucose = String.valueOf(dataSnapshot.child("glucose level").getValue());
                    spo2 = String.valueOf(dataSnapshot.child("spo2").getValue());
                    pulserate= String.valueOf(dataSnapshot.child("pulse rate").getValue());
                    bpdown = String.valueOf(dataSnapshot.child("bp down").getValue());
                    progressText.setText(spo2+"%");
                    progressText1.setText(glucose);
                    progressText3.setText(bp+"/"+bpdown);
                    progressText4.setText(pulserate);

                    //SPO2 Level Condition
                    int a=Integer.parseInt(spo2);
                    if (a>=95) {
                        spo21="Normal Level";
                        relativeLayout.setBackgroundResource(R.drawable.background);
                    }else if (a>=90){
                        spo21="Average Level";
                        relativeLayout.setBackgroundResource(R.drawable.background);
                    }else {
                        spo21="Low Level";
                        relativeLayout.setBackgroundResource(R.drawable.background1);
                    }

                    //Glucose level condition
                    int b=Integer.parseInt(glucose);
                    if (b>=126) {
                        glucose1="High Glucose Level";
                        relativeLayout1.setBackgroundResource(R.drawable.background1);
                    }else if (b>=70  && b<126){
                        glucose1="Normal Glucose Level";
                        relativeLayout1.setBackgroundResource(R.drawable.background);
                    }else {
                        glucose1="Low Glucose Level";
                        relativeLayout1.setBackgroundResource(R.drawable.background1);
                    }

                    //BP Condition
                    int systolic = Integer.parseInt(bp);
                    int diastolic = Integer.parseInt(bpdown);

                    if (systolic >= 130 && diastolic >= 80) {
                        bp1="High Blood Pressure";
                        relativeLayout3.setBackgroundResource(R.drawable.background1);
                    } else if (systolic < 90 || diastolic < 60) {
                        bp1="Low Blood Pressure";
                        relativeLayout3.setBackgroundResource(R.drawable.background1);
                    } else {
                        bp1="Normal Blood Pressure";
                        relativeLayout3.setBackgroundResource(R.drawable.background);
                    }

                    //Pulse Rate Condition
                    int pulse = Integer.parseInt(pulserate);

                    if (pulse >= 100) {
                        pulserate1="High Pulse Rate";
                        relativeLayout4.setBackgroundResource(R.drawable.background1);
                    } else if (pulse < 60) {
                        pulserate1="Low Pulse Rate";
                        relativeLayout4.setBackgroundResource(R.drawable.background1);
                    } else {
                        pulserate1="Normal Pulse Rate";
                        relativeLayout4.setBackgroundResource(R.drawable.background);
                    }
                }
            }
        });

        reference1.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    height = String.valueOf(dataSnapshot.child("height").getValue());
                    weight = String.valueOf(dataSnapshot.child("weight").getValue());
                    smoke = String.valueOf(dataSnapshot.child("smoking").getValue());
                    w=Float.parseFloat(weight);
                    h=Float.parseFloat(height);
                    h=h/100;
                    w=w/(h*h);
                    bmi=String.valueOf(w);
                    progressText2.setText(bmi);

                    //Smoking Condition
                   if (smoke.equals("Never")) {
                        smoke1="Healthy";
                        progress="0";
                       relativeLayout5.setBackgroundResource(R.drawable.background);
                    } else if (smoke.equals("Monthly")){
                        smoke1="Unhealthy";
                        progress="1";
                       relativeLayout5.setBackgroundResource(R.drawable.background1);
                    }
                    else if (smoke.equals("Weekly")) {
                        smoke1="Harmful";
                        progress="2";
                        relativeLayout5.setBackgroundColor(Color.parseColor("#FFFF0000"));
                    } else if (smoke.equals("Daily")) {
                        smoke1="Destructive";
                        progress="3";
                       relativeLayout5.setBackgroundColor(Color.parseColor("#FFA500"));
                    }
                    else
                        progress="NA";
                    progressText5.setText(progress);

                    //BMI condition
                    if (w>=25) {
                        bmi1="You are Overweight";
                        relativeLayout2.setBackgroundResource(R.drawable.background1);
                    }else if (w>=18.5  && w<25){
                        bmi1="You are Healthy";
                        relativeLayout2.setBackgroundResource(R.drawable.background);
                    }else {
                        bmi1="You are Underweight";
                        relativeLayout2.setBackgroundResource(R.drawable.background1);
                    }

                }
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewContactDialog();
            }
        });

        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPersonContactDialog();
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewBusinessContactDialog();
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewContactDialog3();
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewContactDialog4();
            }
        });

        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewContactDialog5();
            }
        });
        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawer = findViewById(R.id.drawer);
        NavigationView nav = findViewById(R.id.navigation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawer.closeDrawer(GravityCompat.START);
                switch (id){
                    case R.id.nav_home:
                        startActivity(new Intent(MainActivity2.this,HomeActivity.class));
                        finish();
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(MainActivity2.this,LoginActivity.class));
                        finish();
                        break;
                    case R.id.nav_manual:
                        startActivity(new Intent(MainActivity2.this,ManualActivity.class));
                        finish();
                        break;
                    case R.id.nav_device:
                        startActivity(new Intent(MainActivity2.this,EntryActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });



    }

    public void createNewContactDialog(){
        dialogBuilder=new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.popup ,null);
        tv1 = contactPopupView.findViewById(R.id.text1);
        tv2 = contactPopupView.findViewById(R.id.text2);
        tv3 = contactPopupView.findViewById(R.id.text3);
        iv = contactPopupView.findViewById(R.id.image1);
        ConstraintLayout constraintLayout = contactPopupView.findViewById(R.id.cl);
                tv1.setText("Your SPO2 Percentage Is");
                tv2.setText(spo2+"%");
                tv3.setText(spo21);
                iv.setImageResource(R.drawable.spo2level);
                int a=Integer.parseInt(spo2);
                if (a>=95) {
                    constraintLayout.setBackgroundResource(R.drawable.background);
                }else if (a>=90){
                    constraintLayout.setBackgroundResource(R.drawable.background);
                }else {
                    constraintLayout.setBackgroundResource(R.drawable.background1);
                }
        dialogBuilder.setView(contactPopupView);
        dialog=dialogBuilder.create();
        dialog.show();
    }

    public void createNewPersonContactDialog(){
        dialogBuilder=new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.popup ,null);
        tv1 = contactPopupView.findViewById(R.id.text1);
        tv2 = contactPopupView.findViewById(R.id.text2);
        tv3 = contactPopupView.findViewById(R.id.text3);
        iv = contactPopupView.findViewById(R.id.image1);
        ConstraintLayout constraintLayout = contactPopupView.findViewById(R.id.cl);
            tv1.setText("Your Glucose Level Is");
            tv2.setText(glucose);
            tv3.setText(glucose1);
            iv.setImageResource(R.drawable.glucoselevel);
            int b=Integer.parseInt(glucose);
            if (b>=126) {
               constraintLayout.setBackgroundResource(R.drawable.background1);
            }else if (b>=70  && b<126){
               constraintLayout.setBackgroundResource(R.drawable.background);
            }else {
                constraintLayout.setBackgroundResource(R.drawable.background1);
        }

        dialogBuilder.setView(contactPopupView);
        dialog=dialogBuilder.create();
        dialog.show();
    }

    public void createNewBusinessContactDialog(){
        dialogBuilder=new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.popup ,null);
        tv1 = contactPopupView.findViewById(R.id.text1);
        tv2 = contactPopupView.findViewById(R.id.text2);
        tv3 = contactPopupView.findViewById(R.id.text3);
        iv = contactPopupView.findViewById(R.id.image1);
        ConstraintLayout constraintLayout = contactPopupView.findViewById(R.id.cl);

        tv1.setText("Your Body Mass Index Is");
        tv2.setText(bmi);
        tv3.setText(bmi1);
        iv.setImageResource(R.drawable.bmi);

        double c=Double.parseDouble(bmi);
        if (c>=25) {
            constraintLayout.setBackgroundResource(R.drawable.background1);
        }else if (c>=18.5  && c<25){
            constraintLayout.setBackgroundResource(R.drawable.background);
        }else {
            constraintLayout.setBackgroundResource(R.drawable.background1);
        }

        dialogBuilder.setView(contactPopupView);
        dialog=dialogBuilder.create();
        dialog.show();
    }

    public void createNewContactDialog3(){
        dialogBuilder=new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.popup ,null);
        tv1 = contactPopupView.findViewById(R.id.text1);
        tv2 = contactPopupView.findViewById(R.id.text2);
        tv3 = contactPopupView.findViewById(R.id.text3);
        iv = contactPopupView.findViewById(R.id.image1);
        ConstraintLayout constraintLayout = contactPopupView.findViewById(R.id.cl);

        tv1.setText("Your Blood Pressure Is");
        tv2.setText(bp+"/"+bpdown);
        tv3.setText(bp1);
        iv.setImageResource(R.drawable.bloodpressure);

        int systolic = Integer.parseInt(bp);
        int diastolic = Integer.parseInt(bpdown);

        if (systolic >= 130 && diastolic >= 80) {
            constraintLayout.setBackgroundResource(R.drawable.background1);
        } else if (systolic < 90 || diastolic < 60) {
            constraintLayout.setBackgroundResource(R.drawable.background1);
        } else {
            constraintLayout.setBackgroundResource(R.drawable.background);
        }

        dialogBuilder.setView(contactPopupView);
        dialog=dialogBuilder.create();
        dialog.show();
    }

    public void createNewContactDialog4(){
        dialogBuilder=new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.popup ,null);
        tv1 = contactPopupView.findViewById(R.id.text1);
        tv2 = contactPopupView.findViewById(R.id.text2);
        tv3 = contactPopupView.findViewById(R.id.text3);
        iv = contactPopupView.findViewById(R.id.image1);
        ConstraintLayout constraintLayout = contactPopupView.findViewById(R.id.cl);

        tv1.setText("Your Pulse Rate Is");
        tv2.setText(pulserate);
        tv3.setText(pulserate1);
        iv.setImageResource(R.drawable.pulserate);
        int pulse = Integer.parseInt(pulserate);

        if (pulse >= 100) {
            constraintLayout.setBackgroundResource(R.drawable.background1);
        } else if (pulse < 60) {
            constraintLayout.setBackgroundResource(R.drawable.background1);
        } else {
            constraintLayout.setBackgroundResource(R.drawable.background);
        }

        dialogBuilder.setView(contactPopupView);
        dialog=dialogBuilder.create();
        dialog.show();

    }

    public void createNewContactDialog5(){
        dialogBuilder=new AlertDialog.Builder(this);
        final View contactPopupView=getLayoutInflater().inflate(R.layout.popup ,null);
        tv1 = contactPopupView.findViewById(R.id.text1);
        tv2 = contactPopupView.findViewById(R.id.text2);
        tv3 = contactPopupView.findViewById(R.id.text3);
        iv = contactPopupView.findViewById(R.id.image1);
        ConstraintLayout constraintLayout = contactPopupView.findViewById(R.id.cl);

        tv1.setText("You Smoke");
        tv2.setText(smoke);
        tv3.setText(smoke1);
        iv.setImageResource(R.drawable.smoking);

        if (smoke.equals("Never")) {
            constraintLayout.setBackgroundResource(R.drawable.background);
        } else if (smoke.equals("Monthly")){
            constraintLayout.setBackgroundResource(R.drawable.background1);
        }
        else if (smoke.equals("Weekly")) {
            constraintLayout.setBackgroundColor(Color.parseColor("#FFFF0000"));
        } else{
          constraintLayout.setBackgroundColor(Color.parseColor("#FFA500"));
        }
        dialogBuilder.setView(contactPopupView);
        dialog=dialogBuilder.create();
        dialog.show();

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity2.this,EntryActivity.class));
        finish();
    }
}