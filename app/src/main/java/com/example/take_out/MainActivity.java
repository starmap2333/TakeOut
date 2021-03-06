package com.example.take_out;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.take_out.adapters.BottomNavAdapter;
import com.example.take_out.component.HomePageFragment;
import com.example.take_out.component.LoginFragment;
import com.example.take_out.component.MainOrderFragment;
import com.example.take_out.component.SettingFragment;
import com.example.take_out.component.SharingFragment;
import com.example.take_out.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Fragment> fragments;
    private int myPage = 4;

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
        fragments.add(HomePageFragment.newInstance());
        fragments.add(SharingFragment.newInstance(2));
        fragments.add(MainOrderFragment.newInstance());
        fragments.add(SettingFragment.newInstance());
        fragments.add(LoginFragment.newInstance());
    }

    private void initView() {
        binding.viewPager.setAdapter(new BottomNavAdapter(this, fragments));
        binding.viewPager.setUserInputEnabled(false);
        binding.bottomNav.setOnNavigationItemSelectedListener(
                item -> {
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
                        case R.id.my_page_nav: {
                            binding.viewPager.setCurrentItem(myPage, false);
                            return true;
                        }
                        default:
                            return false;
                    }
                }
        );
        observeUserState();
    }

    void observeUserState() {
        TakeOutApplication app = (TakeOutApplication) getApplication();
        app.getUserLiveData().observe(this, user -> {
            if (user.getId() == -1) {
                myPage = 4;
            } else {
                myPage = 3;
                binding.bottomNav.setSelectedItemId(R.id.my_page_nav);
            }
        });
    }
}


