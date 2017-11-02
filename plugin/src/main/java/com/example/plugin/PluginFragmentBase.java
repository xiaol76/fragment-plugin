package com.example.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.IFragmentInterface;
import com.example.common.IFragmentNavigator;

import java.lang.reflect.Method;

/**
 * Created by wangyongliang on 17/9/26.
 */

public abstract class PluginFragmentBase extends Fragment implements IFragmentInterface {
    public static final String FRAGMENT_KEY_APK_PATH = "fragment_key_apk_path";

    private AssetManager mAssetManager;
    private Resources mResources;
    public IFragmentNavigator mNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getSelfResources().getLayout(getLayoutResourceId()), null);
        innerOnCreateView(view);
        return view;
    }

    protected abstract int getLayoutResourceId();

    protected abstract void innerOnCreateView(View view);

    public AssetManager getSelfAssets() {
        if (mAssetManager == null) {
            String apkPath = getArguments().getString(FRAGMENT_KEY_APK_PATH);
            if (apkPath != null && apkPath.length() > 0) {
                mAssetManager = createAssetManager(apkPath);
            }
        }
        return mAssetManager;
    }

    public Resources getSelfResources() {
        if (mResources == null) {
            mResources = createResource(getActivity(), getSelfAssets());
        }
        return mResources;
    }

    public void setNavigator(IFragmentNavigator navigator) {
        mNavigator = navigator;
    }

    public static AssetManager createAssetManager(String dexPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Resources createResource(Context context, AssetManager assetManager) {
        Resources superRes = context.getResources();
        return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }
}
