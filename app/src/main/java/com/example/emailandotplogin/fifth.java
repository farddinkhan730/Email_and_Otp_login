package com.example.emailandotplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class fifth extends AppCompatActivity {
    EditText e1;
    Button b1;
    FirebaseAuth firebaseAuth;
    String phone,otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        e1=findViewById(R.id.editTextPhone2);
        b1=findViewById(R.id.button6);
        firebaseAuth=FirebaseAuth.getInstance();
        phone=getIntent().getStringExtra("mobile".toString());
        getotp();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().isEmpty()){
                    Toast.makeText(fifth.this, "Plz fill otp", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (e1.getText().toString().length()!=6){
                        Toast.makeText(fifth.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otp,e1.getText().toString());
                        signInwithPhoneAuthCredential(credential);
                    }
                }

            }
        });
    }
    private void getotp(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone
                , 60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otp=s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInwithPhoneAuthCredential(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(fifth.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signInwithPhoneAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(fifth.this, "Database updated", Toast.LENGTH_SHORT).show();
                        Intent j=new Intent(fifth.this,Forth.class);
                        startActivity(j);
                        finish();
                    }
                    else {
                        Toast.makeText(fifth.this, "Mismatch", Toast.LENGTH_SHORT).show();

                    }
            }
        });

    }
}