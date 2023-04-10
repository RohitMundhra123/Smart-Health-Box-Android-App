package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    EditText edName, edPhone, edAge, edWeight, edHeight;
    private RadioGroup radioGroup, radioGroup2;
    RadioButton b1,b2,b3,b4,b5,b6,b7;
    private RadioButton selectedradioButton, selectedradioButton2;
    Button btn, btn1, btn2;
    FirebaseUser fuser;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edAge = findViewById(R.id.editTextAge);
        edWeight = findViewById(R.id.editTextWeight);
        edHeight = findViewById(R.id.editTextHeight);
        edName = findViewById(R.id.editName);
        edPhone = findViewById(R.id.editPhone);
        btn = findViewById(R.id.buttonSubmit);
        btn1=findViewById(R.id.buttonDiscard);
        b1=findViewById(R.id.male);
        b2 = findViewById(R.id.female);
        b3 = findViewById(R.id.others);
        b4 = findViewById(R.id.never);
        b5 =findViewById(R.id.weekly);
        b6 =findViewById(R.id.monthly);
        b7 =findViewById(R.id.daily);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);
        progressDialog = new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Data");
        reference2 = firebaseDatabase.getReference("Information");

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
                        startActivity(new Intent(EditActivity.this,HomeActivity.class));
                        finish();
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(EditActivity.this,LoginActivity.class));
                        finish();
                        break;
                    case R.id.nav_info:
                        startActivity(new Intent(EditActivity.this,InfoActivity.class));
                        finish();
                        break;
                    case R.id.nav_entry:
                        startActivity(new Intent(EditActivity.this,EntryActivity.class));
                        finish();
                        break;
                    case R.id.nav_aboutus:
                        startActivity(new Intent(EditActivity.this, TeamActivity.class));
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

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String name = String.valueOf(dataSnapshot.child("name").getValue());
                    String phone = String.valueOf(dataSnapshot.child("phone").getValue());
                    edName.setText(name);
                    edPhone.setText(phone);

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
                    if (gender.equals(b1.getText().toString())) {
                        b1.setChecked(true);
                    }
                    else if (gender.equals(b2.getText().toString())){
                        b2.setChecked(true);
                    }
                    else if (gender.equals(b3.getText().toString())) {
                        b3.setChecked(true);
                    }
                    edAge.setText(age);
                    edHeight.setText(height);
                    edWeight.setText(weight);
                    if (smoking.equals(b4.getText().toString())) {
                        b4.setChecked(true);
                    } else if (smoking.equals(b5.getText().toString())){
                        b5.setChecked(true);
                    }
                    else if (smoking.equals(b6.getText().toString())) {
                        b6.setChecked(true);
                    } else if (smoking.equals(b7.getText().toString())) {
                        b7.setChecked(true);
                    }
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedradioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                selectedradioButton2 = radioGroup2.findViewById(radioGroup2.getCheckedRadioButtonId());
                Information();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditActivity.this,InfoActivity.class));
        finish();
    }

    private void Information() {
        String name = edName.getText().toString();
        String phone = edPhone.getText().toString();
        String age = edAge.getText().toString();
        String gender = selectedradioButton.getText().toString();
        String height = edHeight.getText().toString();
        String weight = edWeight.getText().toString();
        String smoking = selectedradioButton2.getText().toString();

        if(name.length()==0){
            edName.setError("Field Empty");
        } else if (phone.length() == 0) {
            edPhone.setError("Field Empty");
        } else if (age.length() == 0) {
            edAge.setError("Field Empty");
        } else if (gender.length() == 0) {
            selectedradioButton.setError("Field Empty");
        } else if (height.length() == 0) {
            edHeight.setError("Field Empty");
        } else if (weight.length() == 0) {
            edWeight.setError("Field Empty");
        } else if (smoking.length() == 0) {
            selectedradioButton2.setError("Field Empty");
        } else {
            progressDialog.setMessage("Please Wait");
            progressDialog.setTitle("Editing");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            saveInfo(name, phone, age, gender, height, weight, smoking);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditActivity.this, EditActivity2.class));

        }
    }

    private void saveInfo(String name, String phone, String age, String gender, String height, String weight, String smoking) {
            String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
            reference=firebaseDatabase.getReference("Data");
            HashMap<String, String> user=new HashMap<String, String>();
            user.put("name",name);
            user.put("phone",phone);

        reference.child(uid).setValue(user);

        reference2 = firebaseDatabase.getReference("Information");
            HashMap<String, String> userMap=new HashMap<String, String>();
            userMap.put("age",age);
            userMap.put("gender",gender);
            userMap.put("height",height);
            userMap.put("weight",weight);
            userMap.put("smoking",smoking);
            reference2.child(uid).setValue(userMap);
    }
}