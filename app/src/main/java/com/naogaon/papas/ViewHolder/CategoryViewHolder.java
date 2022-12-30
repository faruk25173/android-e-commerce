package com.naogaon.papas.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.naogaon.papas.Interface.ItemClickListner;
import com.naogaon.papas.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtcatName;
    public ImageView imageView;
    public ItemClickListner listner;
    public CategoryViewHolder(View itemView)


    {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtcatName = (TextView) itemView.findViewById(R.id.product_name);

        imageView.setOnClickListener(this);
        txtcatName.setOnClickListener(this);

    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;

    }


    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}

