package com.example.soundboxplugindemo;

import android.content.Context;
import android.os.SystemClock;

import com.example.common.IAPIModule;
import com.example.common.Promise;

import java.util.Map;

/**
 * Created by wangyongliang on 17/9/26.
 */

public class APIModuleImpl implements IAPIModule {

    @Override
    public void fetch(Context context, Map<String, String> params, final Promise promise) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                promise.resolve("response ok");
            }
        }).start();
    }
}
