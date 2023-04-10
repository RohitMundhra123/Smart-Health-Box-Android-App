package com.example.smarthealthbox;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private static final int Request_Enable=0;
    private static final int Request_Discoverable=1;

    Button btn;
    TextView textView;

    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textView=findViewById(R.id.textView);
        btn = findViewById(R.id.analyze);

        mBlueAdapter= BluetoothAdapter.getDefaultAdapter();

        if(mBlueAdapter==null){
            textView.setText("Bluetooth Is not Available");
        }

        else{
            textView.setText("Bluetooth Is Available");
        }
    }
}