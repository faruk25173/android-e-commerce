package com.naogaon.papas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.naogaon.papas.Model.Products;
import com.naogaon.papas.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {
    private DatabaseReference ProductRef;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;;
    private String categoryId = "";
    Toolbar  toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_product);


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        ProductRef = FirebaseDatabase.getInstance().getReference().child("products");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        toolbar=findViewById(R.id.toolbar);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                intent.putExtra("catId",  categoryId);
                startActivity(intent);
                finish();
            }
        });
        
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("categoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            Toast.makeText(this, "" + categoryId, Toast.LENGTH_SHORT).show();
            loadproduct(categoryId);
        }
        else{

            Intent intent=new Intent(ProductActivity.this, HomeActivity.class);
            Toast.makeText(ProductActivity.this, "Try again from Category Page", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }

    }

    protected void loadproduct(String categoryId) {
        Query searchBycategoryId = ProductRef.orderByChild("menuId").equalTo(categoryId);

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(searchBycategoryId, Products.class)
                        .build();

        final FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    final FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=this;
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "Tk.");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                            holder.setItemClickListner((view, position0, isLongClick) -> {
                            Intent intent = new Intent(ProductActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("productID", adapter.getRef(position0).getKey());
                            startActivity(intent);
                            finish();
                    });




                       //holder.itemView.setOnClickListener(view -> {
                           //Intent category = new Intent(ProductActivity.this, ProductDetailsActivity.class);
                           //intent.putExtra("productid", model.getProductid());
                           //category.putExtra("productID", adapter.getRef(position).getKey());
                           //startActivity(category);
                           //finish();
                           //startActivity(intent);
                      // });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}