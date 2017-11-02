package com.example.common;

import android.content.Context;

import java.util.Map;

/**
 * Created by wangyongliang on 17/9/26.
 */

public interface IAPIModule {
    void fetch(Context context, Map<String, String> params, Promise promise);
}
