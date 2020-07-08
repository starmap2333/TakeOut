package com.example.take_out.component;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.take_out.R;
import com.example.take_out.data.MyOrder;
import com.example.take_out.databinding.FragmentMainorderBinding;
import com.example.take_out.service.ServiceKt;
import com.example.take_out.viewmodels.OrderModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentMainorderBinding binding;
    CheckBox checkBox;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View orderView;
    private OrderClassRecyclerView mAdapter_test;
    private RecyclerView recyclerView_test;
    private LinearLayoutManager mLayoutManager_test;

    private OrderModel orderModel;
    private List<MyOrder> list_order;

    public MainOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment_MainOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static MainOrderFragment newInstance(String param1, String param2) {
        MainOrderFragment fragment = new MainOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderModel = new ViewModelProvider(this).get(OrderModel.class);

        /*
         *获取后端数据
         */


        orderModel.getOrderList().observe(getViewLifecycleOwner(), ordersList -> {
            mAdapter_test.setData(ordersList);
            list_order = ordersList;
//            ServiceKt.loadUrl(imgview, getContext(), "uuid",);
            mAdapter_test.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        orderView = inflater.inflate(R.layout.fragment_mainorder, container, false);
        binding = FragmentMainorderBinding.bind(orderView);
        recyclerView_test = binding.recycleviewMainorder;
        recyclerView_test.setHasFixedSize(true);
        recyclerView_test.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        mLayoutManager_test = new LinearLayoutManager(getContext());
        mLayoutManager_test.setOrientation(RecyclerView.VERTICAL);
        recyclerView_test.setLayoutManager(mLayoutManager_test);


        mAdapter_test = new OrderClassRecyclerView(getContext(), list_order, (view, position) -> {
            //操作某一个外观。
            Log.d("tag", String.valueOf(position));
            checkBox = view.findViewById(R.id.radioButton_shopname);
            if (!checkBox.isChecked()) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

        });
//        binding.checkfororder.setOnClickListener(v -> {
//            recyclerView_test.setAdapter(mAdapter_test);
//        });
        recyclerView_test.setAdapter(mAdapter_test);


        binding.btnTotal.setOnClickListener(v -> {
                    binding.btnTotal.setText(R.string.total);
                    binding.btnReadytopay.setText("待付款");
                    binding.btnReadytofood.setText("待点餐");
                    binding.btnReadytotalk.setText("待评价");
                    binding.btnOtherside.setText("退款/售后");
                    showAllItem();
                }
        );
        binding.btnReadytopay.setOnClickListener(v -> {
            binding.btnReadytopay.setText(R.string.readyPay);
            binding.btnTotal.setText("全部");
            binding.btnReadytofood.setText("待点餐");
            binding.btnReadytotalk.setText("待评价");
            binding.btnOtherside.setText("退款/售后");

            /*
             *待付款选择
             */


            mAdapter_test.setData(list_order);
        });
        click_notRequired();

        binding.checkfororder.setOnClickListener(v -> {
            recyclerView_test.setAdapter(mAdapter_test);
            List data = mAdapter_test.getData();
            mAdapter_test.setData(data);
            Toast.makeText(getContext(), "确认订单。", Toast.LENGTH_SHORT).show();
        });



        return orderView;
    }

    public void showAllItem() {
        //list_select.clear();
        mAdapter_test.setData(list_order);
    }


    public void click_notRequired() {
        binding.btnReadytofood.setOnClickListener(v -> {
            binding.btnReadytofood.setText(R.string.readyfood);
            binding.btnTotal.setText("全部");
            binding.btnReadytopay.setText("待付款");
            binding.btnReadytotalk.setText("待评价");
            binding.btnOtherside.setText("退款/售后");
        });
        binding.btnReadytotalk.setOnClickListener(v -> {
            binding.btnReadytotalk.setText(R.string.readytalk);
            binding.btnTotal.setText("全部");
            binding.btnReadytopay.setText("待付款");
            binding.btnReadytofood.setText("待点餐");
            binding.btnOtherside.setText("退款/售后");
        });
        binding.btnOtherside.setOnClickListener(v -> {
            binding.btnOtherside.setText(R.string.other);
            binding.btnTotal.setText("全部");
            binding.btnReadytopay.setText("待付款");
            binding.btnReadytotalk.setText("待评价");
            binding.btnReadytofood.setText("待点餐");
        });
    }
}

class OrderClassRecyclerView extends RecyclerView.Adapter<OrderClassRecyclerView.ViewHolder> {
    OrderClassRecyclerView.OnItemClickListener listener;
    private List<MyOrder> list_recycler;
    private Context context;

    public OrderClassRecyclerView(Context context, List list, OrderClassRecyclerView.OnItemClickListener listener) {
        this.context = context;
        this.list_recycler = list;
        this.listener = listener;
    }

    List getData() {
        return this.list_recycler;
    }

    void setData(List data) {
        this.list_recycler = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(
                v -> {
                    listener.onItemClick(v, position);
                }
        );

        /*
         *try in here
         */

        ServiceKt.loadUrl(holder.imageView, context,
                list_recycler.get(position).getCuisine().getImageUUID());
        holder.item_list.setText(String.valueOf(list_recycler.get(position).getPrice()));

        if (list_recycler.get(position).getState()) {
            holder.textView_orderstate.setText("已完成");
        } else {
            holder.textView_orderstate.setText("未完成");
        }

        holder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return list_recycler.size();
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_list;
        public ImageView imageView;
        public CheckBox checkBox;
        public TextView textView_orderstate;

        public ViewHolder(View view) {
            super(view);
            item_list = view.findViewById(R.id.textView_price);
            imageView = view.findViewById(R.id.image);
            checkBox = view.findViewById(R.id.radioButton_shopname);
            textView_orderstate = view.findViewById(R.id.textView_orderState);
        }
    }
}