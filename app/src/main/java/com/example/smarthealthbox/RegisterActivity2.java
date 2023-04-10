package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Any;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity2 extends AppCompatActivity {

    EditText edUsername, edPassword, edMail, edPhone, edConfirmPassword;
    Button btn;
    TextView tv;
    FirebaseAuth fauth;
    FirebaseUser fuser;
    ProgressDialog progressDialog;
    String userID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        edUsername = findViewById(R.id.editTextRegUsername);
        edMail = findViewById(R.id.editTextMail);
        edPhone = findViewById(R.id.editTextPhoneNumber);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirmPassword = findViewById(R.id.editTextRegConfirmPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);
        fauth = FirebaseAuth.getInstance();
        fuser = fauth.getCurrentUser();
        progressDialog=new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity2.this, LoginActivity.class));
            }
        });
    }

    private void PerforAuth() {
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();
        String mail = edMail.getText().toString();
        String phone = edPhone.getText().toString();
        String confirm = edConfirmPassword.getText().toString();

        if (username.length() == 0) {
            edUsername.setError("Field Empty");
        } else if (password.length() == 0) {
            edPassword.setError("Field Empty");
        } else if (mail.length() == 0) {
            edMail.setError("Field Empty");
        } else if (phone.length() == 0) {
            edPhone.setError("Field Empty");
        } else if (confirm.length() == 0) {
            edConfirmPassword.setError("Field Empty");
        } else {
            if (phone.length() != 10) {
                edPhone.setError("Invalid Phone Number");
            } else if (password.compareTo(confirm) == 1) {
                edConfirmPassword.setError("Password Does not Match");
            } else if (password.length() < 8) {
                edPassword.setError("Password must be 8 character long");
            } else {
                progressDialog.setMessage("Please Wait");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                fauth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveuserInfo(username,mail,phone);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity2.this, InformationActivity.class));
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void saveuserInfo(String username, String mail, String phone) {
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = firebaseDatabase.getReference("Data");

        HashMap<String, String> userMap=new HashMap<String, String>();
        userMap.put("name",username);
        userMap.put("email",mail);
        userMap.put("phone",phone);

        reference.child(uid).setValue(userMap);
    }
}