package com.sanjay.laravel.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sanjay.laravel.AppConstants;
import com.sanjay.laravel.MyApplication;
import com.sanjay.laravel.R;
import com.sanjay.laravel.models.products.ProductsResponse;

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
        if (mProductsList.get(position).getAvailable() == 1) {
            holder.mCardView.setCardBackgroundColor(mcontext.getResources().getColor(R.color.card_bg));
        } else {
            holder.mCardView.setCardBackgroundColor(mcontext.getResources().getColor(R.color.product_not_available));
        }
//        holder.productavailable.setText(mProductsList.get(position).getAvailable().toString());
        String imageUrl = mProductsList.get(position).getProductimg();
        Glide.with(MyApplication.getContext()).load(AppConstants.BASE_URL + imageUrl).into(holder.productimg);

    }


    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productname, productcompany, productamount, productdescription, productavailable;
        private ImageView productimg;
        private CardView mCardView;

        public ViewHolder(View view) {
            super(view);
            mCardView = itemView.findViewById(R.id.product_card);
            productname = view.findViewById(R.id.product_name);
            productcompany = view.findViewById(R.id.product_company);
            productamount = view.findViewById(R.id.product_amount);
            productdescription = view.findViewById(R.id.product_description);
            productavailable = view.findViewById(R.id.product_available);
            productimg = view.findViewById(R.id.product_img);


        }
    }
}
