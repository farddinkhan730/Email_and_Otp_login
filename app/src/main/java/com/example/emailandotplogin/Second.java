package com.example.emailandotplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Second extends AppCompatActivity {
    Button b1,b2;
    EditText e1,e2;
    FirebaseAuth firebaseAuth;
    ProgressBar p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        b1=findViewById(R.id.button3);
        b2=findViewById(R.id.button4);
        e1=findViewById(R.id.editTextTextEmailAddress);
        e2=findViewById(R.id.editTextTextPassword);
        e2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        firebaseAuth=FirebaseAuth.getInstance();
        p1=findViewById(R.id.progressBar);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Second.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                if(s1.isEmpty()){
                    e2.setError("Plz fill Email");
                    return;
                }
                else {
                    if(s2.isEmpty()){
                        e2.setError("plz fill password");
                        return;
                    }
                }
                p1.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(s1,s2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Second.this, "Data updated Sucessful", Toast.LENGTH_SHORT).show();
                            p1.setVisibility(View.INVISIBLE);
                            Intent j=new Intent(Second.this,Forth.class);
                            startActivity(j);
                            finish();
                        }
                        else {
                            Toast.makeText(Second.this, "Data base not updated wrong details", Toast.LENGTH_SHORT).show();
                            p1.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

    }
}