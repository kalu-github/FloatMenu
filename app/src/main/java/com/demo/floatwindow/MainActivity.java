package com.demo.floatwindow;

import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

        findViewById(R.id.showFloatWindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatSub floatWindow = getFloatWindow();
                if (null != floatWindow) {
                    floatWindow.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
