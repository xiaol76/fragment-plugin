package com.example.common;

/**
 * Created by wangyongliang on 17/9/26.
 */

public interface Promise {
    void resolve(Object var1);

    void reject(Throwable throwable);
}
