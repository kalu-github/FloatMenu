package com.demo.floatwindow;

import android.view.View;

/**
 * description: 点击监听
 * created by kalu on 2017/3/14 17:35
 */
public interface OnFloatClickListener {

    /**
     * 点击孩子
     *
     * @param view
     */
    void onClickFloatWindow(View view);

    /**
     * 点击关闭
     */
    void onClickFloatClose();
}
