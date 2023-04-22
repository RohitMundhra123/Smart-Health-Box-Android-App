package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ManualActivity extends AppCompatActivity {

    EditText edpulse, edglucose, edblood, edspo, edblooddown;
    Button btn1, btn2;
    FirebaseUser fuser;
    ProgressDialog progressDialog;
    String userID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

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
                switch (id) {
                    case R.id.nav_home:
                        startActivity(new Intent(ManualActivity.this, HomeActivity.class));
                        finish();
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(ManualActivity.this, LoginActivity.class));
                        finish();
                        break;
                    case R.id.nav_manual:
                        startActivity(new Intent(ManualActivity.this, ManualActivity.class));
                        finish();
                        break;
                    case R.id.nav_device:
                        startActivity(new Intent(ManualActivity.this, EntryActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });

        imageSlider = findViewById(R.id.image_slider);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.bloodpressure, null));
        images.add(new SlideModel(R.drawable.pulserate, null));
        images.add(new SlideModel(R.drawable.spo2level, null));
        images.add(new SlideModel(R.drawable.glucoselevel, null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

        edpulse = findViewById(R.id.editTextGPulse);
        edglucose = findViewById(R.id.editTextGlucose);
        edblood = findViewById(R.id.editTextBp);
        edspo = findViewById(R.id.editTextspo);
        edblooddown = findViewById(R.id.editTextBpdown);
        btn1 = findViewById(R.id.buttonSubmit);
        btn2 = findViewById(R.id.buttonBack);
        progressDialog = new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManualActivity.this, EntryActivity.class));
            }
        });
    }

    private void Information() {
        String blood = edblood.getText().toString();
        String spo = edspo.getText().toString();
        String pulse = edpulse.getText().toString();
        String glucose = edglucose.getText().toString();
        String bpdown = edblooddown.getText().toString();
        int b = Integer.parseInt(blood);
        int s = Integer.parseInt(spo);
        int p = Integer.parseInt(pulse);
        int g = Integer.parseInt(glucose);
        if (blood.length() == 0) {
            edblood.setError("Field Empty");
        } else if (spo.length() == 0) {
            edspo.setError("Field Empty");
        } else if (pulse.length() == 0) {
            edpulse.setError("Field Empty");
        } else if (bpdown.length() == 0) {
            edblooddown.setError("Field Empty");
        } else if (glucose.length() == 0) {
            edglucose.setError("Field Empty");
        } else {
            if (s > 100) {
                edspo.setError("Invalid SPO2 Percentage");
            } else {
                progressDialog.setMessage("Please Wait");
                progressDialog.setTitle("Submitting");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                saveInfo(blood, spo, pulse, glucose, bpdown);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ManualActivity.this, MainActivity2.class));
            }
        }
    }

    private void saveInfo(String blood, String spo, String pulse, String glucose, String bpdown) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = firebaseDatabase.getReference("Health");
        HashMap<String, String> userMap = new HashMap<String, String>();
        userMap.put("blood pressure", blood);
        userMap.put("bp down", bpdown);
        userMap.put("spo2", spo);
        userMap.put("pulse rate", pulse);
        userMap.put("glucose level", glucose);

        reference.child(uid).setValue(userMap);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ManualActivity.this, EntryActivity.class));
        finish();
    }
}
