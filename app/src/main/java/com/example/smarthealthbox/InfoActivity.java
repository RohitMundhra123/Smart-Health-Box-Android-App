package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoActivity extends AppCompatActivity {

    FloatingActionButton fab;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference2;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        fab=findViewById(R.id.edit);
        MaterialToolbar toolbar=findViewById(R.id.topAppbar);
        DrawerLayout drawer=findViewById(R.id.drawer);
        NavigationView nav=findViewById(R.id.navigation);
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
                        startActivity(new Intent(InfoActivity.this,HomeActivity.class));
                        finish();
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(InfoActivity.this,LoginActivity.class));
                        finish();
                        break;
                    case R.id.nav_info:
                        startActivity(new Intent(InfoActivity.this,InfoActivity.class));
                        finish();
                        break;
                    case R.id.nav_entry:
                        startActivity(new Intent(InfoActivity.this,EntryActivity.class));
                        finish();
                        break;
                    case R.id.nav_aboutus:
                        startActivity(new Intent(InfoActivity.this, TeamActivity.class));
                        finish();
                        break;
                    case R.id.nav_mail:
                        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:20051894@kiit.ac.in")));
                        break;
                    case R.id.nav_Whatsapp:
                        String phoneNumber = "7726021453";
                        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                }
                return true;
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Data");
        reference2 = firebaseDatabase.getReference("Information");

        tv1 = findViewById(R.id.getName);
        tv2 = findViewById(R.id.getPhone);
        tv3 = findViewById(R.id.getGender);
        tv4 = findViewById(R.id.getAge);
        tv5 = findViewById(R.id.getHeight);
        tv6 = findViewById(R.id.getWeight);
        tv7 = findViewById(R.id.getSmoking);
        tv8 = findViewById(R.id.getWorking);
        tv9 = findViewById(R.id.getResidence);
        tv10 = findViewById(R.id.getMarital);

        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String name = String.valueOf(dataSnapshot.child("name").getValue());
                    String phone = String.valueOf(dataSnapshot.child("phone").getValue());
                    tv1.setText(name);
                    tv2.setText(phone);

                }
            }
        });
        reference2.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String gender = String.valueOf(dataSnapshot.child("gender").getValue());
                    String age = String.valueOf(dataSnapshot.child("age").getValue());
                    String height = String.valueOf(dataSnapshot.child("height").getValue());
                    String weight = String.valueOf(dataSnapshot.child("weight").getValue());
                    String smoking = String.valueOf(dataSnapshot.child("smoking").getValue());
                    String work = String.valueOf(dataSnapshot.child("work").getValue());
                    String residence = String.valueOf(dataSnapshot.child("residence").getValue());
                    String marital = String.valueOf(dataSnapshot.child("marital").getValue());
                    tv3.setText(gender);
                    tv4.setText(age);
                    tv5.setText(height);
                    tv6.setText(weight);
                    tv7.setText(smoking);
                    tv8.setText(work);
                    tv9.setText(residence);
                    tv10.setText(marital);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoActivity.this,EditActivity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(InfoActivity.this,HomeActivity.class));
        finish();
    }
}