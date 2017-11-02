package com.example.plugin;

import android.view.View;
import android.widget.TextView;

/**
 * Created by wangyongliang on 17/9/27.
 */

public class PluginFragment2 extends PluginFragmentBase {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.plugin_fragment2;
    }

    @Override
    protected void innerOnCreateView(View view) {
        TextView message = (TextView) view.findViewById(R.id.message);
        message.setText("PLUGIN2");
    }
}
