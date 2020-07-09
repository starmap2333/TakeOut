package com.example.take_out.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.take_out.R;
import com.example.take_out.data.Cuisine;
import com.example.take_out.service.ServiceKt;

import java.util.List;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.ViewHolder> {
    private MutableLiveData<List<Cuisine>> data;
    private Context context;

    public CuisineAdapter(Context context, MutableLiveData<List<Cuisine>> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuisine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final List<Cuisine> _data = data.getValue();
        final Cuisine cuisine = _data.get(position);

        ServiceKt.loadUrl(holder.img, context, cuisine.getImageUUID());
        holder.tv_fname.setText(cuisine.getName());
        holder.tv_fprice.setText(String.valueOf(cuisine.getPrice()));

        holder.btn_cut.setOnClickListener(v -> {
            if (cuisine.getNum() >= 1) {
                cuisine.setNum(cuisine.getNum() - 1);
                _data.set(position, cuisine);
                data.setValue(_data);
                holder.tv_fnum.setText(String.valueOf(cuisine.getNum()));
            }
        });

        holder.btn_add.setOnClickListener(v -> {
            cuisine.setNum(cuisine.getNum() + 1);
            _data.set(position, cuisine);
            data.setValue(_data);
            holder.tv_fnum.setText(String.valueOf(cuisine.getNum()));
        });

    }


    @Override
    public int getItemCount() {
        return data.getValue().size();
    }

    public List<Cuisine> getData() {
        return this.data.getValue();
    }

    public void setData(List<Cuisine> cuisines) {
        this.data.setValue(cuisines);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_fname;
        TextView tv_fprice;
        TextView tv_fnum;
        ImageView img;
        Button btn_add;
        Button btn_cut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fname = itemView.findViewById(R.id.recyclerfood_tx_name);
            tv_fprice = itemView.findViewById(R.id.recyclerfood_tx_price);
            tv_fnum = itemView.findViewById(R.id.tx_num);
            img = itemView.findViewById(R.id.recyclerfood_item_image);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_cut = itemView.findViewById(R.id.btn_cut);

        }
    }


}
