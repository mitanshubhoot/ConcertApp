package com.example.eventhomescreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    private TextView textViewUsername, textViewLocation, textViewEmail;
    private String email,password;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    //private static final String USERS = "Users";
    private static FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String userKey = currentUser.getUid();

/*
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("/Users");
        dbRef.addValueEventListener(changeListener);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
*/
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewEmail = findViewById(R.id.textViewEmail);


        //dbRef = database.getReference(USERS);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userKey);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //for (DataSnapshot ds: dataSnapshot.getChildren()){
                   // if(ds.child("email").getValue().equals(email)){

                        String Username = dataSnapshot.child("username").getValue().toString();
                        String Location = dataSnapshot.child("location").getValue().toString();
                        String Email = dataSnapshot.child("email").getValue().toString();
                        textViewUsername.setText(Username);
                        textViewLocation.setText(Location);
                        textViewEmail.setText(Email);
                    //}

               // }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
