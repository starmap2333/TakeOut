package com.example.take_out;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.take_out.adapters.BottomNavAdapter;
import com.example.take_out.databinding.ActivityMainBinding;
import com.example.take_out.module.sharing.SharingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        initData();
        initView();
        setContentView(binding.getRoot());
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(BlankFragment.newInstance("", ""));
        fragments.add(SharingFragment.newInstance(2));
        fragments.add(BlankFragment.newInstance("", ""));
        fragments.add(BlankFragment.newInstance("", ""));
    }

    private void initView() {
        binding.viewPager.setAdapter(new BottomNavAdapter(this, fragments));
        binding.viewPager.setUserInputEnabled(false);
        binding.bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_page_nav:
                                binding.viewPager.setCurrentItem(0, false);
                                return true;
                            case R.id.sharing_page_nav:
                                binding.viewPager.setCurrentItem(1, false);
                                return true;
                            case R.id.order_page_nav:
                                binding.viewPager.setCurrentItem(2, false);
                                return true;
                            case R.id.my_page_nav:
                                binding.viewPager.setCurrentItem(3, false);
                                return true;
                            default:
                                return false;
                        }
                    }
                }
        );
    }
}
