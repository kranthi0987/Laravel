package com.sanjay.laravel.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sanjay.laravel.R;
import com.sanjay.laravel.app.AppConstants;
import com.sanjay.laravel.app.MyApplication;
import com.sanjay.laravel.models.products.ProductsResponse;
import com.sanjay.laravel.views.activties.ViewProduct;

import java.util.ArrayList;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ViewHolder> {

    private ArrayList<ProductsResponse> mProductsList;
    private Context mcontext;


    public ProductDataAdapter(ArrayList<ProductsResponse> productlist, Context context) {
        mProductsList = productlist;
        mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productname.setText(mProductsList.get(position).getName());
        holder.productdescription.setText(mProductsList.get(position).getDescription());
        holder.productamount.setText(mProductsList.get(position).getAmount().toString() + "₹");
        holder.productcompany.setText(mProductsList.get(position).getCompany());
        holder.productcategory.setText(mProductsList.get(position).getCategory());
        if (mProductsList.get(position).getAvailable() == 1) {
            holder.mCardView.setCardBackgroundColor(mcontext.getResources().getColor(R.color.card_bg));
        } else {
            holder.mCardView.setCardBackgroundColor(mcontext.getResources().getColor(R.color.product_not_available));
        }
//        holder.productavailable.setText(mProductsList.get(position).getAvailable().toString());
        String imageUrl = mProductsList.get(position).getProductimg();
        Glide.with(MyApplication.getContext()).load(AppConstants.BASE_URL + imageUrl).into(holder.productimg);
        holder.productbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mcontext, ViewProduct.class);
                i.putExtra("productid", mProductsList.get(position).getId());
                mcontext.startActivity(i);
                Toast.makeText(mcontext, "button clicked with id" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productname, productcompany, productamount, productdescription, productavailable, productcategory;
        private ImageView productimg;
        private CardView mCardView;
        private Button productbtn;

        public ViewHolder(View view) {
            super(view);
            mCardView = itemView.findViewById(R.id.product_card);
            productname = view.findViewById(R.id.product_name);
            productcompany = view.findViewById(R.id.product_company);
            productamount = view.findViewById(R.id.product_amount);
            productdescription = view.findViewById(R.id.product_description);
            productavailable = view.findViewById(R.id.product_available);
            productimg = view.findViewById(R.id.product_img);
            productcategory = view.findViewById(R.id.product_category);
            productbtn = view.findViewById(R.id.product_btn);
        }
    }

    public void filterList(ArrayList<ProductsResponse> filterdNames) {
        this.mProductsList = filterdNames;
        notifyDataSetChanged();
    }
}


