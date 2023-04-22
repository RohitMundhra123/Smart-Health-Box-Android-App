package com.example.smarthealthbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.net.Uri;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class EntryActivity extends AppCompatActivity {

    Button manual;
    Button device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

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
                         startActivity(new Intent(EntryActivity.this,HomeActivity.class));
                         finish();
                         break;
                     case R.id.nav_logout:
                         startActivity(new Intent(EntryActivity.this,LoginActivity.class));
                         finish();
                         break;
                     case R.id.nav_info:
                         startActivity(new Intent(EntryActivity.this,InfoActivity.class));
                         finish();
                         break;
                     case R.id.nav_entry:
                         startActivity(new Intent(EntryActivity.this,EntryActivity.class));
                         finish();
                         break;
                     case R.id.nav_aboutus:
                         startActivity(new Intent(EntryActivity.this, TeamActivity.class));
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

        manual=findViewById(R.id.button2);
        device=findViewById(R.id.button3);

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EntryActivity.this,ManualActivity.class));
            }
        });

        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EntryActivity.this,BluetoothActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(EntryActivity.this,HomeActivity.class));
        finish();
    }

}