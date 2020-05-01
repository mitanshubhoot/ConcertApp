package com.example.eventhomescreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterNew extends AppCompatActivity {

    Button autoFill, Register;
    EditText Name, Email, Contact;
    String concertTitle, concertCategory, concertNo, concertTime,concertDate,concertLocation;
    int icon, concert_no;
    private static final int uniqueID = 12345;
    NotificationCompat.Builder notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        notification = new NotificationCompat.Builder(this, "default");
        notification.setAutoCancel(false);

        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            Bundle b = intent.getExtras();
            if (b != null) {
                concertNo = b.getString("key");
                concertTitle = b.getString("title");
                concertDate = b.getString("date");
                concertLocation = b.getString("location");
                concertTime = b.getString("time");
                concertCategory = b.getString("category");
                concert_no = Integer.parseInt(concertNo);
            }


            autoFill = (Button) findViewById(R.id.autofill);
            Register = (Button) findViewById(R.id.register);
            Name = (EditText) findViewById(R.id.registerName);
            Email = (EditText) findViewById(R.id.registerEmail);
            Contact = (EditText) findViewById(R.id.registerContact);
            autoFill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Name.setText(Global.name);
                    Email.setText(Global.email);
                    Contact.setText(Global.contact);
                }
            });
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do something;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    DatabaseReference myRef = database.getReference().child("Users").child(email.substring(0,email.length()-10)).child("Concerts").child(concertNo);

                    myRef.child("Time").setValue(concertTime);
                    myRef.child("Date").setValue(concertDate);
                    myRef.child("Location").setValue(concertLocation);
                    myRef.child("Category").setValue(concertCategory);
                    myRef.child("Title").setValue(concertTitle);
                    myRef = database.getReference().child("Concerts").child("Registrations").child(concertTitle).child(email.substring(0,email.length()-10));
                    myRef.child("Name").setValue(Name.getText().toString());
                    myRef.child("Contact").setValue(Contact.getText().toString());
                    myRef.child("Email").setValue(Email.getText().toString());

                    Toast.makeText(getApplicationContext(),
                            "Registered Successfully", Toast.LENGTH_LONG).show();

/*


                    String register_message = "You have successfully registered for the event";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            RegisterNew.this
                    )
                            .setSmallIcon(R.drawable.ic_message)
                            .setContentTitle("Confirmation Message")
                            .setContentText(register_message)
                            .setAutoCancel(true);

                    Intent intent1 = new Intent(RegisterNew.this,
                            NotificationActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtra( "register_message", register_message);

                    PendingIntent pendingIntent = PendingIntent.getActivity(RegisterNew.this,
                            0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);

                    NotificationManager notificationManager = (NotificationManager)getSystemService(
                            Context.NOTIFICATION_SERVICE
                    );
                    notificationManager.notify(0,builder.build());*/


                }
            });
        }
    }

    public void notifyButtonClicked(View view) {


        notification.setSmallIcon(R.drawable.icon);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        notification.setLargeIcon(bitmap);
        notification.setTicker("This is Title");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Confirmation Message");
        notification.setContentText("You have successfully registered for the event");

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }
}
