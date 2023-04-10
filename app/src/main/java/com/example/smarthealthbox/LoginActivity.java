package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btn;
    TextView tv;
    FirebaseAuth fauth;
    FirebaseUser fuser;
    ProgressDialog progressDialog;
    int counter=0;
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        imageSlider= findViewById(R.id.image_slider);

        ArrayList<SlideModel> images=new ArrayList<>();
        images.add(new SlideModel(R.drawable.slide1, null));
        images.add(new SlideModel(R.drawable.slide2, null));
        images.add(new SlideModel(R.drawable.slide3, null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

        edUsername= findViewById(R.id.editTextLoginUsername);
        edPassword= findViewById(R.id.editTextLoginPassword);
        btn= findViewById(R.id.buttonLogin);
        tv= findViewById(R.id.textViewNewUser);
        fauth = FirebaseAuth.getInstance();
        fuser = fauth.getCurrentUser();
        progressDialog=new ProgressDialog(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity2.class));
            }
        });
    }

    private void PerformLogin() {
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();

        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Field Empty", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Please Wait");
            progressDialog.setTitle("Sign In");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            fauth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,SplashActivity2.class));
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Username And Password Does not Match" , Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
finish();
    }
}