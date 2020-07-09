package com.example.take_out.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.take_out.R;
import com.example.take_out.data.MyOrder;
import com.example.take_out.service.ServiceKt;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    private List<MyOrder> data;
    private Context context;

    public MyOrdersAdapter(Context context, List<MyOrder> data) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_myorder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyOrder myOrder = data.get(position);
        holder.tv_mfname.setText(myOrder.getCuisine().getName());
        holder.tv_mfprice.setText(String.valueOf(myOrder.getPrice()));
        holder.tv_mfnum.setText(myOrder.getNum());
        ServiceKt.loadUrl(holder.img, context, myOrder.getCuisine().getImageUUID());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mfname;
        TextView tv_mfnum;
        TextView tv_mfprice;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mfname = itemView.findViewById(R.id.food_name_myorder);
            tv_mfnum = itemView.findViewById(R.id.food_num_myorder);
            tv_mfprice = itemView.findViewById(R.id.food_price_myorder);
            img = itemView.findViewById(R.id.food_img_myorder);
        }
    }
}
