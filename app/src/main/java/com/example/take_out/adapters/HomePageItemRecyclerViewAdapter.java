package com.example.take_out.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.take_out.R;
import com.example.take_out.data.Store;
import com.example.take_out.service.ServiceKt;

import java.util.List;

public class HomePageItemRecyclerViewAdapter extends RecyclerView.Adapter<HomePageItemRecyclerViewAdapter.ViewHolder> {
    OnItemClickListener onItemClickListener;
    //  private List<Integer> heights = new ArrayList<>();
    private List<Store> data;
    private Context context;

    public HomePageItemRecyclerViewAdapter(Context context, List<Store> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<Store> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载item布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller, parent, false);
        //new一个ViewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      /*  heights.add(300);
        heights.add(300);
        ViewGroup.LayoutParams layoutParams =holder.image.getLayoutParams();
        layoutParams.height = heights.get(position);*/
        Store store = data.get(position);
        holder.tv_store_name.setText(store.getName());
        holder.tv_store_description.setText(store.getDescription());
        ServiceKt.loadUrl(holder.image, context, store.getImageUUID());

        holder.cardview.setOnClickListener(v ->
                onItemClickListener.onItemClick(holder.itemView, position, store));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /*点击事件*/
        void onItemClick(View view, int position, Store store);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_store_name;
        ImageView image;
        CardView cardview;
        TextView tv_store_description;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            tv_store_name = itemView.findViewById(R.id.recyclerseller_tx_seller);
            image = itemView.findViewById(R.id.recyclerseller_item_image);
            cardview = itemview.findViewById(R.id.recyclerseller_cardview);
            tv_store_description = itemview.findViewById(R.id.recyclerseller_tx_description);
        }
    }
}

