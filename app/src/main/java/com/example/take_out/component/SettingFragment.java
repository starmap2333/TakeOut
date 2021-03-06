package com.example.take_out.component;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.take_out.R;
import com.example.take_out.TakeOutApplication;
import com.example.take_out.databinding.FragmentSettingBinding;
import com.example.take_out.service.ServiceKt;

import org.jetbrains.annotations.NotNull;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    final int REQUEST_PERMISSION = 1;
    public AMapLocationClient mLocationClient = null;
    /*
     *总的全局变量在这里
     */
    FragmentSettingBinding binding;

    private String[] infos = new String[]{
            "美食1：费用0000￥",
            "美食2：费用0000￥",
            "美食3：费用0000￥",
            "美食4：费用0000￥"};
    private String[] infos_share = new String[]{
            "这是一个记录1",
            "这是一个记录2",
            "这是一个记录3",
            "这是一个记录4"
    };
    private int[] order_image = new int[]{
            R.drawable.order_chuangxiang,
            R.drawable.order_danta,
            R.drawable.order_ganguoji,
            R.drawable.order_meishi
    };

    private String location;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    Log.i("TAG", "当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i("TAG", "纬度 ----------------" + amapLocation.getLatitude());//获取纬度
                    Log.i(TAG, "经度-----------------" + amapLocation.getLongitude());//获取经度
                    Log.i(TAG, "精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
                    Log.i(TAG, "地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i(TAG, "国家信息-------------" + amapLocation.getCountry());//国家信息
                    Log.i(TAG, "省信息---------------" + amapLocation.getProvince());//省信息
                    Log.i(TAG, "城市信息-------------" + amapLocation.getCity());//城市信息
                    Log.i(TAG, "城区信息-------------" + amapLocation.getDistrict());//城区信息
                    Log.i(TAG, "街道信息-------------" + amapLocation.getStreet());//街道信息
                    Log.i(TAG, "街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
                    Log.i(TAG, "城市编码-------------" + amapLocation.getCityCode());//城市编码
                    Log.i(TAG, "地区编码-------------" + amapLocation.getAdCode());//地区编码
                    Log.i(TAG, "当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
                    location = amapLocation.getAddress();
                    Toast.makeText(getContext(), "当前地址:" + location, Toast.LENGTH_LONG).show();
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    void checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSION);//自定义的code
        }
    }

    void setupRecyclerViewSharing() {
        binding.recyclerViewSharing.setHasFixedSize(true);
        binding.recyclerViewSharing.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recyclerViewSharing.setLayoutManager(layoutManager);
        binding.recyclerViewSharing.setAdapter(new MySharingAdapter());
    }

    void setupRecyclerViewOrder() {
        binding.recyclerViewOrder.setHasFixedSize(true);
        binding.recyclerViewOrder.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewOrder.setLayoutManager(layoutManager);
        binding.recyclerViewOrder.setAdapter(new MyOrderAdapter());
    }

    void setup() {
        setupRecyclerViewSharing();
        setupRecyclerViewOrder();

        binding.imageButtonFragUserdesign.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserDesignActivity.class);
            startActivity(intent);
        });

        binding.spinnerSelectlocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Toast.makeText(getContext(),"设置为默认位置",Toast.LENGTH_LONG).show();
                    location = "重庆";
                }
                if (position == 1) {
                    startLocation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.buttonAbout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SettingAboutActivity.class);
            startActivity(intent);
        });

        ((TakeOutApplication) requireActivity().getApplication()).getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            ServiceKt.loadUrl(binding.imageView, requireContext(), user.getImageUUID());
            binding.textViewUsername.setText(user.getName());
        });
    }



    /*
     *其他函数写在这里
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        setup();
        return binding.getRoot();
    }

    public void startLocation() {
        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(mLocationListener);

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private class MySharingAdapter extends RecyclerView.Adapter<MySharingAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_sharing, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.item_list.setText(infos_share[position]);
        }

        @Override
        public int getItemCount() {
            return infos_share.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView item_list;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                item_list = view.findViewById(R.id.text_view);
                imageView = view.findViewById(R.id.imageView_item);
            }
        }
    }


    public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_order, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.item_list.setText(infos[position]);
            holder.imageView_order.setImageResource(order_image[position]);
            if (position == 3) {
                holder.button_order.setText("已支付");
                holder.button_order.setBackgroundColor(R.color.black);
            }
        }

        @Override
        public int getItemCount() {
            return infos.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView item_list;
            public ImageView imageView_order;
            public Button button_order;

            public ViewHolder(View view) {
                super(view);
                item_list = view.findViewById(R.id.recycler_view_text);
                imageView_order = view.findViewById(R.id.recycler_view_image);
                button_order = view.findViewById(R.id.recycle_view_btn);
            }
        }


    }
}
