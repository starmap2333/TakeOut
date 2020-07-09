package com.example.take_out.component;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.take_out.R;
import com.example.take_out.adapters.CuisineAdapter;
import com.example.take_out.data.Cuisine;
import com.example.take_out.data.Store;
import com.example.take_out.viewmodels.CuisineModel;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    private RecyclerView foodview;
    private MutableLiveData<List<Cuisine>> data = new MutableLiveData<>(new ArrayList<>());
    private List<Cuisine> _buy = new ArrayList<>();
    private CuisineModel cuisineModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Button btn_back = findViewById(R.id.backto);
        Button btn_payoff = findViewById(R.id.btn_payoff);
        btn_back.setOnClickListener(new ButtonListener());
        btn_payoff.setOnClickListener(new ButtonListener());
        Store store = getIntent().getParcelableExtra("store");
        cuisineModel = new ViewModelProvider(this,
                new CuisineModel.ViewModelFactory(store)).get(CuisineModel.class);
        initcuisine();
    }


    private void initcuisine() {
        foodview = findViewById(R.id.recycler_food);

        cuisineModel.getCuisineList().observe(this, cuisines -> {
            data.setValue(cuisines);
        });

        //创建布局管理
        foodview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        data.observe(this, cuisines -> {
            double total = 0.0;
            for (Cuisine cuisine : cuisines) {
                total += cuisine.getNum() * cuisine.getPrice();
            }
            //保留小数点后两位
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            nf.setMaximumFractionDigits(2);

            ((Button) findViewById(R.id.btn_money)).setText(nf.format(total));
        });

        //给RecyclerView设置适配器
        foodview.setAdapter(new CuisineAdapter(this, data));

        foodview.addItemDecoration(new RecycleItemDecoration(5));

        cuisineModel.getCuisineList().observe(this, cuisines -> {
            ((CuisineAdapter) foodview.getAdapter()).setData(cuisines);
        });
    }


    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backto:
                    finish();
                    break;
                case R.id.btn_payoff:
                    //将数量大于1的商品的信息提到_buy列表,把_buy列表传出去
                    List<Cuisine> data = ((CuisineAdapter) foodview.getAdapter()).getData();
                    for (Cuisine cuisine : data) {
                        if (cuisine.getNum() >= 1) {
                            _buy.add(cuisine);
                        }
                    }
                    Intent intent = new Intent(StoreActivity.this, PurchaseActivity.class);
                    intent.putExtra("list", (Serializable) _buy);
                    startActivity(intent);

                    break;
                default:
                    break;
            }
        }
    }
}