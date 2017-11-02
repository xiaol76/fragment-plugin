package com.example.common;

/**
 * Created by wangyongliang on 17/9/26.
 */

public class ModuleManager {
    private static final ModuleManager mModuleManager = new ModuleManager();

    private IAPIModule mAPIModule;

    private ModuleManager() {
    }

    public static ModuleManager getInstance() {
        return mModuleManager;
    }

    public void setAPIModule(IAPIModule apiModule) {
        mAPIModule = apiModule;
    }

    public IAPIModule getAPIModule() {
        return mAPIModule;
    }
}
