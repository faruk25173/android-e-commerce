package com.naogaon.papas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
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
import io.paperdb.Paper;
import io.paperdb.Book;

public class LoginActivity extends AppCompatActivity {
    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton,RememberButton,SaveButton, ClearButton,RemoveButton;
    private TextView registerLink, forgetpassword ;
    private ProgressDialog loadingBar;
    private String parentDbName = "users";
   // private CheckBox chkBoxRememberMe;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Phone = "nameKey";
    public static final String Password= "emailKey";
    String phone;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = (Button) findViewById(R.id.login_btn);
        SaveButton = (Button) findViewById(R.id.save_button);
        ClearButton= (Button) findViewById(R.id.ClearButton);
        RememberButton = (Button) findViewById(R.id.remember_button);
        //RemoveButton = (Button) findViewById(R.id.RemoveButton);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        registerLink = (TextView) findViewById(R.id.admin_panel_link);
        forgetpassword = (TextView) findViewById(R.id.forget_password_link);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Phone)) {
            InputPhoneNumber.setText(sharedpreferences.getString(Phone, ""));
        }
        if (sharedpreferences.contains(Password)) {
            InputPassword.setText(sharedpreferences.getString(Password, ""));

        }



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

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordCheck.class);
                startActivity(intent);
                finish();

            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        save();
            }
        });
        RememberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    get();
            }
        });

        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clear();
            }
        });

       // ClearButton.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View view) {

             //   remove();
        //    }
       // });

    }

    private void save() {
        String n = InputPhoneNumber.getText().toString();
        String e = InputPassword.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, n);
        editor.putString(Password, e);
        editor.commit();
    }

    private void get() {

        InputPhoneNumber= (EditText) findViewById(R.id.login_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Phone)) {
            InputPhoneNumber.setText(sharedpreferences.getString(Phone, ""));
        }
        if (sharedpreferences.contains(Password)) {
            InputPassword.setText(sharedpreferences.getString(Password, ""));

        }
    }

    private void clear() {
        InputPhoneNumber= (EditText) findViewById(R.id.login_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber.setText("");
        InputPassword.setText("");
    }
   // private void remove() {
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.remove(Phone);
       // editor.remove(Password);
        //editor.apply();
    //}
    private void LoginUser() {
        phone = InputPhoneNumber.getText().toString();
        password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone, password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {

                            if (parentDbName.equals("users")) {
                                Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, SlideMainActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }

                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}