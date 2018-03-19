package com.jtv.videodemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jtv.videodemo.R;
import com.jtv.videodemo.adapter.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_menu;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_menu = findViewById(R.id.rv_menu);

        List<String> menuList = new ArrayList<>();
        List<Class> activityList = new ArrayList<>();
        {
            menuList.add("TBS播放");
            activityList.add(TBSActivity.class);

            menuList.add("友盟分享");
            activityList.add(UShareActivity.class);
        }

        rv_menu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rv_menu.setAdapter(new MenuAdapter(this,menuList,activityList));
    }
}
