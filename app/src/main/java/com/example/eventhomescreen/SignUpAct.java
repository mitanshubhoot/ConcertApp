package com.example.eventhomescreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
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

                signUp(editMail.getText().toString(),editPassword.getText().toString(), editLocation.getText().toString(), editUserName.getText().toString());

            }
        });

    }

    private void signUp(final String email, String password, final String location, final String username){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child("RegisteredUsers").child(email.substring(0,email.length()-10));

                            myRef.child("location").setValue(location);
                            myRef.child("username").setValue(username);
                            Toast.makeText(getApplicationContext(), "Signed Up", Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(), HomeScreen.class);
                            startActivity(s);
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
