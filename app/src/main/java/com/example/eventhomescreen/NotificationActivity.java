package com.example.eventhomescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView notif = findViewById(R.id.notif);
        String register_message = getIntent().getStringExtra("register_message");
        notif.setText(register_message);
    }
}
