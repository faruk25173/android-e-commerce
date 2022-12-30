package com.naogaon.papas;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.naogaon.papas.Model.Cart;
import com.naogaon.papas.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,addressEditText;
    private Button confirmOrderBtn,back_button;
    private String totalAmount = "";
    private TextView pinfo,order_number;
    private String categoryId = "";
    Spinner spinnerarea, cityspinner, paymentspinner;
    DatabaseReference dbrefcity, dbrefarea,paymentmoderef ;



    ValueEventListener listener;
    ArrayList<String> listcity, listarea,paymentlist;
    ArrayAdapter<String> cityadapter,areaadapter, paymentlistadapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        categoryId = getIntent().getStringExtra("catId");
        final Random myrandom=new Random();
        cityspinner = (Spinner) findViewById(R.id.city_spinner);
        spinnerarea = (Spinner) findViewById(R.id.area_spinner);
        paymentspinner = (Spinner) findViewById(R.id.payment_spinner);


        pinfo=(TextView) findViewById(R.id.pname);
        order_number=(TextView) findViewById(R.id.txt_order);
        order_number.setText(String.valueOf( myrandom.nextInt(100000)));


        dbrefcity = FirebaseDatabase.getInstance().getReference().child("city");
        dbrefarea = FirebaseDatabase.getInstance().getReference("area");
        paymentmoderef = FirebaseDatabase.getInstance().getReference("payment");


        listcity = new ArrayList<String>();
        cityadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listcity);
        cityspinner.setAdapter( cityadapter);

        listarea = new ArrayList<String>();
        areaadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listarea);
        spinnerarea.setAdapter(areaadapter);

        paymentlist = new ArrayList<String>();
        paymentlistadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, paymentlist);
        paymentspinner.setAdapter(paymentlistadapter);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = Tk. " + totalAmount, Toast.LENGTH_SHORT).show();
        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shippment_name);
        phoneEditText = (EditText) findViewById(R.id.shippment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shippment_address);

        back_button=(Button) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmFinalOrderActivity.this,CartActivity.class);
                intent.putExtra("catId",categoryId);
                startActivity(intent);
                finish();
            }
        });

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();

            }
        });

        DatabaseReference cartlistref = FirebaseDatabase.getInstance().getReference().child("Cart List").child("User view").child(Prevalent.currentOnlineUser.getPhone()).child("products");
        listener = cartlistref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot mydata : snapshot.getChildren()) {

                        Cart cart = mydata.getValue(Cart.class);
                        pinfo.append( cart.getPname() + ", " + cart.getPrice() + ", " + cart.getQuantity()+ "\n");
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        listener = paymentmoderef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot mydata : snapshot.getChildren())
                    paymentlist.add(mydata.getValue().toString());
                paymentlistadapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        fetchdatacity();

    }

    public void fetchdatacity() {

        listener = dbrefcity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata : snapshot.getChildren())
                    listcity.add(mydata.getValue().toString());
                cityadapter.notifyDataSetChanged();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position , long id) {


                        String selected = listcity.get(position).toString();

                        Log.e("Clicked:", "" + selected);

                        reference.child("area").child(selected).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    listarea.clear();
                                }
                                for (DataSnapshot mydata : snapshot.getChildren())
                                    listarea.add(mydata.getValue().toString());

                                areaadapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Full Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Valid Address.",Toast.LENGTH_SHORT).show();
        }

        else {

            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Confirm Order");
        progressDialog.setMessage("Please wait, while we are confirming your order");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final String saveCurrentTime,saveCurrentDate,date_time;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd. yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        date_time = saveCurrentDate + saveCurrentTime;

        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
                .child("Orders");
                

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("pinfo",pinfo.getText().toString());
        ordersMap.put("order_number",order_number.getText().toString());
        ordersMap.put("name",nameEditText.getText().toString());
        ordersMap.put("phone",phoneEditText.getText().toString());
        ordersMap.put("address",addressEditText.getText().toString());
        ordersMap.put("city",cityspinner.getSelectedItem().toString());
        ordersMap.put("area",spinnerarea.getSelectedItem().toString());
        ordersMap.put("payment_mode",paymentspinner.getSelectedItem().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("pid",date_time);
        ordersMap.put("state", "Pending");
        ordersRef.child("Admin Orders").child(Prevalent.currentOnlineUser.getPhone()).updateChildren(ordersMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
			  public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()) {
					  FirebaseDatabase.getInstance().getReference().child("Orders")
							.child("User Orders")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .child("My Orders").push()
                            .updateChildren(ordersMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseDatabase.getInstance().getReference().child("Orders")
                                                .child("Admin Records")
                                                .child(Prevalent.currentOnlineUser.getPhone())
                                                .child("My Orders").push()
                                                .updateChildren(ordersMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Cart List")
                                                .child("User view")
                                                .child(Prevalent.currentOnlineUser.getPhone())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(ConfirmFinalOrderActivity.this, "Your final Order has been placed successfully.", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                    }
                                                });
                                            }
                                         }
                                     });

                                    }
                                }
                            });


                     }
                 }

            });

       }

}


