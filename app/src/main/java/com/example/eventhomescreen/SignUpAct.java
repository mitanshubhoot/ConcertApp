package com.example.eventhomescreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventhomescreen.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpAct extends AppCompatActivity {

    Animation frombottom, fromtop;
    Button btnSignUp, btnSignIn;
    TextView textViewSign;
    EditText editMail, editUserName, editLocation, editPassword;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
/*
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);*/

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(s);
            }
        });

        textViewSign = (TextView) findViewById(R.id.textViewSign);

        editMail = (EditText) findViewById(R.id.editTextSignEmail);
        editUserName = (EditText) findViewById(R.id.editText5);
        editLocation = (EditText) findViewById(R.id.editText3);
        editPassword = (EditText) findViewById(R.id.editText7);
/*
        btnSignUp.startAnimation(frombottom);

        textViewSign.startAnimation(fromtop);

        editMail.startAnimation(fromtop);
        editUserName.startAnimation(fromtop);
        editLocation.startAnimation(fromtop);
        editPassword.startAnimation(fromtop);

*/
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = new User(editUserName.getText().toString(),
                        editPassword.getText().toString(),
                        editMail.getText().toString(),
                        editLocation.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                            Toast.makeText(SignUpAct.this,"The Username already exists",Toast.LENGTH_SHORT).show();
                        else {
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(SignUpAct.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
