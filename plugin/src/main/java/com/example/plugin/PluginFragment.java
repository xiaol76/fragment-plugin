package com.example.plugin;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.common.IAPIModule;
import com.example.common.IFragmentInterface;
import com.example.common.ModuleManager;
import com.example.common.Promise;

import java.util.HashMap;

/**
 * Created by wangyongliang on 17/9/26.
 */

public class PluginFragment extends PluginFragmentBase implements IFragmentInterface {

    @Override
    protected void innerOnCreateView(View view) {
        Button startRequest = (Button) view.findViewById(R.id.start);
        Button forward = (Button) view.findViewById(R.id.forward);
        forward.setText("GO TO PLUGIN1");
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("WYL", "plugin open plugin1 " + mNavigator.toString());
                mNavigator.openPluginFragment("PluginFragment1", true);
            }
        });
        startRequest.setText("START V2");
        final TextView messageView = (TextView) view.findViewById(R.id.message);
        startRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IAPIModule apiModule = ModuleManager.getInstance().getAPIModule();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("test", "request");
                apiModule.fetch(getActivity(), map, new Promise() {
                    @Override
                    public void resolve(final Object var1) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageView.setText((String) var1);
                            }
                        });
                    }

                    @Override
                    public void reject(Throwable throwable) {

                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.plugin_fragment;
    }
}
