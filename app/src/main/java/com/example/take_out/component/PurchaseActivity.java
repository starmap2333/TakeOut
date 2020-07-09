package com.example.take_out.component;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.take_out.R;
import com.example.take_out.TakeOutApplication;
import com.example.take_out.adapters.MyOrdersAdapter;
import com.example.take_out.data.Cuisine;
import com.example.take_out.data.MyOrder;
import com.example.take_out.data.User;
import com.example.take_out.service.MyOrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity implements View.OnClickListener {
    private List<MyOrder> _mydata = new ArrayList<>();
    private RecyclerView myOrderview;
    private List<String> address_list;
    private ArrayAdapter<String> addr_adapter;
    private List<MyOrderService.MyOrderParam> genOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Spinner spinner = findViewById(R.id.spinner_address);
        Button back = findViewById(R.id.back);
        Button purchase = findViewById(R.id.btn_purchase);
        back.setOnClickListener(this);
        purchase.setOnClickListener(this);
        initView();

        address_list = new ArrayList<>();
        address_list.add("11111");
        address_list.add("22222");
        address_list.add("33333");
        address_list.add("44444");
        //适配器
        addr_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, address_list);
        //设置样式
        addr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(addr_adapter);

    }

    private void initView() {
        myOrderview = findViewById(R.id.MyOrder);

        //接受数据
        List<Cuisine> _mbuy = (List<Cuisine>) getIntent().getSerializableExtra("list");
        User user = ((TakeOutApplication) getApplication()).getUser();
        for (Cuisine cuisine : _mbuy) {
            MyOrderService.MyOrderParam myOrderParam =
                    new MyOrderService.MyOrderParam(user.getId(), cuisine.getId(), cuisine.getNum());
            genOrders.add(myOrderParam);

            MyOrder myOrder = new MyOrder(-1, user, cuisine, 0.0, 0, false
                    , new Date());
            _mydata.add(myOrder);
        }


        myOrderview.setAdapter(new MyOrdersAdapter(this, _mydata));

        //添加分割线
        myOrderview.addItemDecoration(new RecycleItemDecoration(10));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();


        }
    }
}