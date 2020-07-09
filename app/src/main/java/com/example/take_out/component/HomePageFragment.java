package com.example.take_out.component;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.take_out.R;
import com.example.take_out.adapters.HomePageItemRecyclerViewAdapter;
import com.example.take_out.data.Store;
import com.example.take_out.databinding.FragmentHomepageBinding;
import com.example.take_out.viewmodels.StoreModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomePageFragment extends Fragment {
    private List<Integer> list = new ArrayList<>();
    private List<Store> data = new ArrayList<>();
    private FragmentHomepageBinding binding;
    private StoreModel storeModel;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storeModel = new ViewModelProvider(this).get(StoreModel.class);
        initRecyclerView();
        initloop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    //初始化轮播图
    private void initloop() {
        list.add(R.drawable.test);
        list.add(R.drawable.test2);
        list.add(R.drawable.test3);
        list.add(R.drawable.test4);
        list.add(R.drawable.test5);

        BannerAdapter adapter = new BannerAdapter(requireContext(), list);
        final RecyclerView recyclerView = binding.recycler;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(list.size() * 10);


        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> recyclerView.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1), 2000, 2000, TimeUnit.MILLISECONDS);
    }

    //初始化商店展示的数据
    private void initRecyclerView() {
        RecyclerView recyclerView = binding.recyclerseller;

        //瀑布流布局
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        HomePageItemRecyclerViewAdapter homePageItemRecyclerViewAdapter = new HomePageItemRecyclerViewAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(homePageItemRecyclerViewAdapter);
        //添加分割线
        recyclerView.addItemDecoration(new RecycleItemDecoration(15));
        //item的点击
        homePageItemRecyclerViewAdapter.setOnItemClickListener((view, position, store)
                -> {
            Intent intent = new Intent(requireContext(), StoreActivity.class)
                    .putExtra("store", store);
            startActivity(intent);
        });

        storeModel.getStoreList().observe(getViewLifecycleOwner(), stores ->
                ((HomePageItemRecyclerViewAdapter) (binding.recyclerseller.getAdapter())).setData(stores));
    }

    //轮播图的Adapter
    public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

        private List<Integer> list;
        private Context context;

        public BannerAdapter(Context context, List<Integer> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loopview, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Glide.with(context).load(list.get(position % list.size())).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_image);
            }
        }

    }

    //重写smoothScrollToPosition()方法,减缓自动轮播切换图片时速度
    public class SmoothLinearLayoutManager extends LinearLayoutManager {

        public SmoothLinearLayoutManager(Context context) {
            super(context);
        }

        public SmoothLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 0.2f; //返回0.2
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }

    }

}


