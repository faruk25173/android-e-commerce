package com.naogaon.papas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.Button;


public class ResetPassword extends AppCompatActivity {

    private Button resetbtn;
    private EditText resetpassword, phone;
    private  String  _resetpassword, _phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);


        resetbtn =(Button) findViewById(R.id.submit);
        resetpassword = (EditText)findViewById(R.id.editTextTextPassword);
        phone =(EditText) findViewById(R.id.phone);
        resetbtn.setOnClickListener(v -> writenewpassword(_phone,_resetpassword));

       _resetpassword=resetpassword.getText().toString().trim();
        _phone=phone.getText().toString().trim();
    }

    private void writenewpassword(String _phone,String _resetpassword) {



        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("users").child(_phone);

        ref.child("password").setValue(_resetpassword)

                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ResetPassword.this, "Your resetting New Password is Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPassword.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
