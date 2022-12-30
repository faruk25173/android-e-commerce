package com.naogaon.papas;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.naogaon.papas.Model.AdminOrders;
import com.naogaon.papas.Prevalent.Prevalent;

import java.util.ArrayList;

public class UserOrderActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference dbrefcity;
    Spinner  cityspinner;
    ValueEventListener listener;
    ArrayList<String> listcity;
    ArrayAdapter<String> cityadapter;
    String district=null;

        @Override
         protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_orders);

        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));

            cityspinner = (Spinner) findViewById(R.id.city_spinner);


            dbrefcity = FirebaseDatabase.getInstance().getReference().child("city");

            listcity = new ArrayList<String>();
            cityadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listcity);




                listener = dbrefcity.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            listcity.clear();
                    }
                        for (DataSnapshot mydata : snapshot.getChildren())
                            listcity.add(mydata.getValue().toString());
                        cityadapter.notifyDataSetChanged();


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
             });


        }



       protected void onStart() {

        super.onStart();



             final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child("User Orders").child(Prevalent.currentOnlineUser.getPhone()).child("My Orders");
        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef,AdminOrders.class)
                        .build();

            FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int i, @NonNull AdminOrders model) {

                        holder.userName.setText("Name: " + model.getName());
                        holder.userPhoneNumber.setText("Phone: " + model.getPhone());
                        holder.userTotalPrice.setText("Total Ammount = Tk." + model.getTotalAmount());
                        holder.userDateTime.setText("Order at: " + model.getDate() + " " + model.getTime());
                        holder.order_number.setText("Order Number: " + model.getDate() + "-" + model.getTime()+ "-" + model.getOrder_number());
                        holder.userShippingAddress.setText("Shipping Address: " + model.getAddress() + ", " + model.getArea()+", " + model.getCity());
                        holder.orderstatus.setText("Order: " + model.getState());
                        holder.payment_mode.setText("Payment Mode: " + model.getPayment_mode());
                        holder.pinfo.setText(model.getPinfo());


                     }


                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_order_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
            ordersList.setAdapter(adapter);
            adapter.startListening();

        }


                public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

                public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress, orderstatus,pinfo,order_number,payment_mode;
                public ImageView img;

                public AdminOrdersViewHolder(View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.order_user_name);
                userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
                userTotalPrice = itemView.findViewById(R.id.order_total_price);
                userDateTime = itemView.findViewById(R.id.order_date_time);
                userShippingAddress = itemView.findViewById(R.id.order_address_city);
                orderstatus = itemView.findViewById(R.id.order_status);
                pinfo=itemView.findViewById(R.id.pinfo);
                order_number=itemView.findViewById(R.id.order_number);
                payment_mode=itemView.findViewById(R.id. payment_mode);
                img = (ImageView) itemView.findViewById(R.id.img1);

                 }

            }


    }
