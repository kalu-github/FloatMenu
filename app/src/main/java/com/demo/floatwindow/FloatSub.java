package com.demo.floatwindow;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * description: 可拖拽的显示容器
 * created by kalu on 2017/3/14 17:35
 */
public class FloatSub extends FrameLayout {

    /**********************************************************************************************/

    public FloatSub(Context context) {
        this(context, null, 0);
    }

    public FloatSub(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatSub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.BLACK);

        // 1.添加蒙层
        // Fix Bug - 这里的context,尽量使用全局的context
        View cover = new View(APP.getInstance().getApplicationContext());
        cover.setClickable(true);
        addView(cover);
        cover.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null == onFloatClickListener) return;
                onFloatClickListener.onClickFloatWindow(v);
            }
        });

        // 2.添加关闭
        // Fix Bug - 这里的context,尽量使用全局的context
        ImageView image = new ImageView(APP.getInstance().getApplicationContext());
        image.setClickable(true);
        int pading = DeviceUtil.dp2px(5);
        image.setPadding(pading, pading, pading, pading);
        image.setImageResource(R.mipmap.ic_launcher);
        image.setBackgroundColor(Color.BLACK);
        addView(image);
        LayoutParams params = (LayoutParams) image.getLayoutParams();
        int size = DeviceUtil.dp2px(25);
        params.width = size;
        params.height = size;
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        image.setLayoutParams(params);
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null == onFloatClickListener) return;
                onFloatClickListener.onClickFloatClose();
            }
        });
    }

    /*********************************************************************************************/

    private OnFloatClickListener onFloatClickListener;

    public void setOnFloatClickListener(OnFloatClickListener onFloatClickListener) {
        this.onFloatClickListener = onFloatClickListener;
    }
}