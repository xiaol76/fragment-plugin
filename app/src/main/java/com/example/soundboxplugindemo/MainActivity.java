package com.example.soundboxplugindemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gotoPluginBtn = (Button) findViewById(R.id.loadPlugin);
        gotoPluginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PluginFragmentActivity.launch(MainActivity.this);
            }
        });
    }
}
