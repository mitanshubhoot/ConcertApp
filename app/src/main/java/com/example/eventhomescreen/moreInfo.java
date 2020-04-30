package com.example.eventhomescreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class moreInfo extends AppCompatActivity {

    String concertTitle, concertIcon, concertCategory, concertNo;
    LinearLayout outer;
    ImageView concertImageView;
    TextView concertTextViewTitle, concertTextViewCategory, infoTitle, infoLocation, infoDate, infoTime;
    Button toRegister;
    int icon;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            Bundle b = intent.getExtras();
            if(b!=null) {
                concertTitle = b.getString("title");
                concertIcon = b.getString("icon");
                concertCategory = b.getString("category");
                icon = Integer.parseInt(concertIcon);
                concertNo = b.getString("key");
            }

        }
        else{
            finish();
        }
        outer = (LinearLayout) findViewById(R.id.info);
        concertImageView = (ImageView) outer.findViewById(R.id.eventpicture);
        concertTextViewTitle = (TextView) outer.findViewById(R.id.eventtitle);
        concertTextViewCategory = (TextView) outer.findViewById(R.id.eventcategory);
        infoTitle = (TextView) findViewById(R.id.info_title);
        infoDate = (TextView) findViewById(R.id.info_date);
        infoLocation = (TextView) findViewById(R.id.info_location);
        infoTime = (TextView) findViewById(R.id.info_time);
        toRegister = (Button) findViewById(R.id.toRegister);

        outer.findViewById(R.id.eventbadge).setAlpha(1);
        infoTitle.setText(concertTitle);
        concertTextViewTitle.setText(concertTitle);
        concertImageView.setImageDrawable(getDrawable(icon));
        concertTextViewCategory.setText(concertCategory);
        Toast.makeText(getApplicationContext(),concertCategory,Toast.LENGTH_LONG).show();
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),RegisterNew.class);
                Bundle b = new Bundle();
                b.putString("title",concertTitle);
                b.putString("category",concertCategory);
                b.putString("time",infoTime.getText().toString());
                b.putString("date",infoDate.getText().toString());
                b.putString("location",infoLocation.getText().toString());
                b.putString("key", concertNo);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
