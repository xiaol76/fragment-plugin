package com.example.plugin;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wangyongliang on 17/9/26.
 */

public class PluginFragment1 extends PluginFragmentBase {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.plugin_fragment1;
    }

    @Override
    protected void innerOnCreateView(View view) {
        TextView message = (TextView) view.findViewById(R.id.message);
        message.setText("PLUGIN1");
        Button button = (Button) view.findViewById(R.id.forward1);
        button.setText("GO TO PLUGIN2");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.openPluginFragment("PluginFragment2", true);
                Log.e("WYL", "plugin1 open plugin2");
            }
        });
    }
}
