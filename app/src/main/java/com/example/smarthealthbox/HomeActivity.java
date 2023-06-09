package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ImageSlider imageSlider;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    TextView tv;
    RelativeLayout r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView t = findViewById(R.id.textHealth);
        t.setSelected(true);

        imageSlider= findViewById(R.id.image_slider);
        r1 = findViewById(R.id.rl1);
        r2 = findViewById(R.id.rl2);

        ArrayList<SlideModel> images=new ArrayList<>();
        images.add(new SlideModel(R.drawable.slide1, null));
        images.add(new SlideModel(R.drawable.slide2, null));
        images.add(new SlideModel(R.drawable.slide3, null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Data");
        tv = findViewById(R.id.TextView);
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot dataSnapshot=task.getResult();
                    String name="Welcome\n"+String.valueOf(dataSnapshot.child("name").getValue());
                    tv.setText(name);
                }
            }
        });
        MaterialToolbar toolbar=findViewById(R.id.topAppbar);
        DrawerLayout drawer=findViewById(R.id.drawer);
        NavigationView nav=findViewById(R.id.navigation);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,EntryActivity.class));
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "7726021453";
                String url = "tel:" + phoneNumber;

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawer.closeDrawer(GravityCompat.START);
                switch (id){
                    case R.id.nav_home:
                        startActivity(new Intent(HomeActivity.this,HomeActivity.class));
                        finish();
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        finish();
                        break;
                    case R.id.nav_info:
                        startActivity(new Intent(HomeActivity.this,InfoActivity.class));
                        finish();
                        break;
                    case R.id.nav_entry:
                        startActivity(new Intent(HomeActivity.this,EntryActivity.class));
                        finish();
                        break;
                    case R.id.nav_aboutus:
                        startActivity(new Intent(HomeActivity.this,TeamActivity.class));
                        finish();
                        break;
                    case R.id.nav_mail:
                        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:abc@gmail.com")));
                        finish();
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
    }

    @Override
    public void onBackPressed() {

        finish();

    }
}