package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class InformationActivity extends AppCompatActivity {

    EditText edAge,edWeight, edHeight;
    private RadioGroup radioGroup,radioGroup2;
    private RadioButton selectedradioButton,selectedradioButton2;
    Button btn;
    FirebaseUser fuser;
    ProgressDialog progressDialog;
    String userID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        edAge = findViewById(R.id.editTextAge);
        edWeight = findViewById(R.id.editTextWeight);
        edHeight = findViewById(R.id.editTextHeight);
        btn = findViewById(R.id.buttonSubmit);
        radioGroup= findViewById(R.id.radioGroup);
        radioGroup2= findViewById(R.id.radioGroup2);
        progressDialog=new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedradioButton=radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                selectedradioButton2=radioGroup2.findViewById(radioGroup2.getCheckedRadioButtonId());
                Information();
            }
        });

    }
    private void Information() {
        String age = edAge.getText().toString();
        String gender = selectedradioButton.getText().toString();
        String height = edHeight.getText().toString();
        String weight = edWeight.getText().toString();
        String smoking = selectedradioButton2.getText().toString();

        if (age.length() == 0) {
            edAge.setError("Field Empty");
        } else if (gender.length() == 0) {
            selectedradioButton.setError("Field Empty");
        } else if (height.length() == 0) {
            edHeight.setError("Field Empty");
        } else if (weight.length() == 0) {
            edWeight.setError("Field Empty");
        } else if (smoking.length() == 0) {
            selectedradioButton2.setError("Field Empty");
        }
        else {
            progressDialog.setMessage("Please Wait");
            progressDialog.setTitle("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        int age1=Integer.parseInt(age);
        int height1=Integer.parseInt(height);
        int weight1=Integer.parseInt(weight);
            saveInfo(age1,gender,height1,weight1,smoking);
            progressDialog.dismiss();
            startActivity(new Intent(InformationActivity.this, InformationActivity2.class));
            finish();
                }
        }

    private void saveInfo(int age, String gender, int height, int weight, String smoking) {
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = firebaseDatabase.getReference("Information/"+uid);
        reference.child("age").setValue(age);
        reference.child("gender").setValue(gender);
        reference.child("height").setValue(height);
        reference.child("weight").setValue(weight);
        reference.child("smoking").setValue(smoking);
    }

}
