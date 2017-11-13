package com.demo.floatwindow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * description 基类Activity
 * created by kalu on 2016/10/23 13:10
 */
public abstract class BaseActivity extends AppCompatActivity implements OnFloatClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        initOrientation();
        setContentView(initView());
        initData();
    }

    /**********************************************************************************************/

    private FloatLayout activityRoot;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        // 根部布局
        activityRoot = new FloatLayout(getApplicationContext());

        // 资源布局
        View customView = View.inflate(getApplicationContext(), layoutResID, null);
        activityRoot.addView(customView, 0);

        super.setContentView(activityRoot);

        // 小窗视频
        if (null != activityRoot) {
            FloatSub floatSub = activityRoot.getFloatSub();
            if (!isFloatSubLis && null != floatSub && floatSub.getVisibility() == View.VISIBLE) {
                isFloatSubLis = true;
                setOnFloatSubClickListener(this);
            }
        }
    }

    private boolean isFloatSubLis = false;

    @Override
    public void onClickFloatWindow(View view) {

        Toast.makeText(getApplicationContext(), "点击小窗", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickFloatClose() {
        Toast.makeText(getApplicationContext(), "点击关闭", Toast.LENGTH_SHORT).show();
    }

    public FloatSub getFloatWindow() {

        if (null == activityRoot) return null;

        FloatSub floatSub = activityRoot.getFloatSub();
        return floatSub;
    }

    /**
     * 浮动窗口点击监听
     *
     * @param listener
     */
    public void setOnFloatSubClickListener(OnFloatClickListener listener) {

        if (null == activityRoot) return;
        activityRoot.setOnFloatSubClickListener(listener);
    }

    public void onResume() {

        // 小窗视频
        if (null != activityRoot) {
            FloatSub floatSub = activityRoot.getFloatSub();
            if (!isFloatSubLis && null != floatSub && floatSub.getVisibility() == View.VISIBLE) {
                isFloatSubLis = true;
                setOnFloatSubClickListener(this);
            }
        }

        super.onResume();
    }

    public void onPause() {

        // 小窗视频
        if (null != activityRoot) {
            FloatSub floatSub = activityRoot.getFloatSub();
            if (null != floatSub && floatSub.getVisibility() == View.VISIBLE) {
                floatSub.setVisibility(View.GONE);
            }
        }

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**********************************************************************************************/

    protected abstract int initView();

    protected abstract void initData();

    /**
     * 默认竖屏
     */
    protected void initOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置点击监听
     */
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (null == listener || ids == null) return;
        for (int id : ids) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * 跳转到指定Activity
     */
    public void go(Intent intent) {
        startActivity(intent);
    }

    /**
     * 跳转到指定Activity
     */
    public void go(Class<?> cls) {
        startActivity(new Intent(getApplicationContext(), cls));
    }
}