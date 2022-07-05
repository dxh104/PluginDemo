package com.dxh.plugindemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
//需要根目录有/storage/sdcard/plugin.apk
public class MainActivity extends AppCompatActivity {

    private Button btnLoadApk;
    private Button btnStartApk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission();

    }


    private void initView() {
        btnLoadApk = (Button) findViewById(R.id.btn_loadApk);
        btnStartApk = (Button) findViewById(R.id.btn_startApk);
        btnLoadApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlugin();
            }
        });
        btnStartApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProxy();
            }
        });
    }

    /**
     * 加载插件
     */
    private void loadPlugin() {
        ///data/data/com.dxh.plugindemo/cache/plugin.apk
        HookManager.getInstance().loadPlugin(this);
        Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
        Log.e("--------", "loadPlugin: ");
    }

    /**
     * 跳转插件
     */
    private void startProxy() {
        Intent intent = new Intent(this, ProxyActivity.class);//这里就是一个占坑的activity
        //这里是拿到我们加载的插件的第一个activity的全类名
        intent.putExtra("ClassName", HookManager.getInstance().packageInfo.activities[0].name);
        startActivity(intent);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//适配6.0权限
            if (ContextCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.RECORD_AUDIO
                        }, 1);
            } else {
                //已经有权限
            }
        }
    }
}
