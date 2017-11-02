package com.example.soundboxplugindemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.common.IFragmentInterface;
import com.example.common.IFragmentNavigator;
import com.example.common.ModuleManager;

import java.io.File;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;

/**
 * Created by wangyongliang on 17/9/30.
 */

public class PluginFragmentActivity extends AppCompatActivity implements IFragmentNavigator {
    private static final String PLUGIN_PATH = Environment.getExternalStorageDirectory() + "/plugin/plugin.apk";
    private static final String DEX_OUTPUT_DIR = "dex";
    private static final String FRAGMENT_KEY_APK_PATH = "fragment_key_apk_path";

    private String mApkPath;
    private String mPackageName;
    private String mCurrentFragmentTag;
    private FragmentManager mFragmentManager;
    private int mFragmentContainerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_plugin);
        ModuleManager.getInstance().setAPIModule(new APIModuleImpl());
        mFragmentManager = getSupportFragmentManager();
        mFragmentContainerId = R.id.fragmentContainer;

        File file = new File(PLUGIN_PATH);
        mApkPath = file.getAbsolutePath();
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(mApkPath, PackageManager.GET_ACTIVITIES);
        mPackageName = packageInfo.packageName;
        openPluginFragment("PluginFragment", false);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, PluginFragmentActivity.class);
        context.startActivity(intent);
    }

    private Fragment getFragmentFromApk(Bundle bundle, String fragmentFullName) {
        try {
            DexClassLoader dexClassLoader = createDexClassLoader(PluginFragmentActivity.this, mApkPath);
            Class<?> remoteClass = dexClassLoader.loadClass(fragmentFullName);
            Constructor<?> localConstructor = remoteClass.getConstructor();
            Fragment fragment = (Fragment) localConstructor.newInstance();
            fragment.setArguments(bundle);
            return fragment;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static DexClassLoader createDexClassLoader(Context context, String apkPath) {
        File dexOutputDir = context.getDir(DEX_OUTPUT_DIR, Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, dexOutputDir.getAbsolutePath(), null, context.getClassLoader());
    }

    @Override
    public void openPluginFragment(String fragmentName, boolean addToBackStack) {
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_KEY_APK_PATH, mApkPath);
        IFragmentInterface pluginFragment = (IFragmentInterface) getFragmentFromApk(bundle, mPackageName + "." + fragmentName);
        if (pluginFragment != null) {
            addFragmentToActivity(pluginFragment, addToBackStack);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void addFragmentToActivity(IFragmentInterface pluginFragment, boolean addToBackStack) {
        pluginFragment.setNavigator(PluginFragmentActivity.this);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(mFragmentContainerId, (Fragment) pluginFragment, pluginFragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(pluginFragment.getClass().getName());
        }
        Fragment hideFragment = null;
        if (!TextUtils.isEmpty(mCurrentFragmentTag)) {
            hideFragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);
        }
        if (hideFragment != null) {
            fragmentTransaction.hide(hideFragment);
        }
        mCurrentFragmentTag = pluginFragment.getClass().getName();
        fragmentTransaction.commitAllowingStateLoss();
    }
}
