package com.naogaon.papas;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naogaon.papas.Model.Users;
import com.naogaon.papas.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;
import io.paperdb.Paper;
import io.paperdb.Book;

public class ResetPasswordCheck extends AppCompatActivity {
    private EditText InputPhoneNumber, favoritepersonname;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private String parentDbName = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        favoritepersonname = (EditText) findViewById(R.id.login_password_input);


        loadingBar = new ProgressDialog(this);
        //chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        // Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        // if (chkBoxRememberMe.isChecked()) {

        //fetch();

        //}

        // else{

        //  Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        // }


    }

    private void LoginUser() {
        String phone = InputPhoneNumber.getText().toString();
        String fpn = favoritepersonname.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(fpn)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone, fpn);
        }
    }

    private void AllowAccessToAccount(final String phone, final String fpn) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getFpn().equals(fpn)) {

                            if (parentDbName.equals("users")) {
                                Toast.makeText(ResetPasswordCheck.this, "Authenticated, Taking to reset password...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(ResetPasswordCheck.this, ResetPassword.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }

                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(ResetPasswordCheck.this, "Favorite Person  Name provided is  incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(ResetPasswordCheck.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}