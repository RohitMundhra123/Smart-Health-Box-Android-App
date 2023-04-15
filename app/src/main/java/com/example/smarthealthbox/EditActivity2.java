
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity2 extends AppCompatActivity {

        private RadioGroup radioGroup,radioGroup2,radioGroup3;
        private RadioButton selectedradioButton1,selectedradioButton2,selectedradioButton3;
        Button btn;
        FirebaseUser fuser;
        ProgressDialog progressDialog;
        String userID;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference reference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit2);

            btn = findViewById(R.id.buttonSubmit);
            radioGroup= findViewById(R.id.radioGroup);
            radioGroup2= findViewById(R.id.radioGroup2);
            radioGroup3= findViewById(R.id.radioGroup3);
            progressDialog=new ProgressDialog(this);
            firebaseDatabase = FirebaseDatabase.getInstance();
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
                            startActivity(new Intent(EditActivity2.this,HomeActivity.class));
                            finish();
                            break;
                        case R.id.nav_logout:
                            startActivity(new Intent(EditActivity2.this,LoginActivity.class));
                            finish();
                            break;
                        case R.id.nav_info:
                            startActivity(new Intent(EditActivity2.this,InfoActivity.class));
                            finish();
                            break;
                        case R.id.nav_entry:
                            startActivity(new Intent(EditActivity2.this,EntryActivity.class));
                            finish();
                            break;
                        case R.id.nav_aboutus:
                            startActivity(new Intent(EditActivity2.this, TeamActivity.class));
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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedradioButton1=radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                selectedradioButton2=radioGroup2.findViewById(radioGroup2.getCheckedRadioButtonId());
                selectedradioButton3=radioGroup3.findViewById(radioGroup3.getCheckedRadioButtonId());
                Information();
                }
            });
        }

        private void Information() {
            String work = selectedradioButton1.getText().toString();
            String residence = selectedradioButton2.getText().toString();
            String marital = selectedradioButton3.getText().toString();

            if (work.length() == 0) {
            selectedradioButton1.setError("Field Empty");
            } else if (residence.length() == 0) {
            selectedradioButton2.setError("Field Empty");
            }else if (marital.length() == 0) {
            selectedradioButton3.setError("Field Empty");
            }
            else {
                progressDialog.setMessage("Please Wait");
                progressDialog.setTitle("Submitting");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                saveInfo(work,residence,marital);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditActivity2.this, InfoActivity.class));
                finish();
            }
        }

        private void saveInfo(String work, String residence, String marital) {
            String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
            reference = firebaseDatabase.getReference("Information/"+uid);
            reference.child("work").setValue(work);
            reference.child("residence").setValue(residence);
            reference.child("marital").setValue(marital);
        }
}
