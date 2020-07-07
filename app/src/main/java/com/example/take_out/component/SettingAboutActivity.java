package com.example.take_out.component;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.take_out.databinding.ActivitySettingAboutBinding;


public class SettingAboutActivity extends AppCompatActivity {
    ActivitySettingAboutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.aboutUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingAboutActivity.this, "目前已是最新版本了哦QWQ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
