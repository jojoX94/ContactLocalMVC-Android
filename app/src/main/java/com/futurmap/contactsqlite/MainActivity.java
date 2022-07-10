package com.futurmap.contactsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.futurmap.contactsqlite.databinding.ActivityMainBinding;
import com.futurmap.contactsqlite.screens.RootActivity;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        normalLaunch();

    }

    public void normalLaunch() {
        binding.animationView.setAnimation(R.raw.hand);
        binding.animationView.animate().setStartDelay(3000);
        binding.animationView.playAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, RootActivity.class));
                finish();
            }
        }, 3000);
    }
}
