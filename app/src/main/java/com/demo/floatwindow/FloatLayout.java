package com.demo.floatwindow;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * description: 可拖拽的父容器
 * created by kalu on 2017/3/14 15:32
 */
public class FloatLayout extends FrameLayout {

    private final int TEMP_SIZE = DeviceUtil.dp2px(5);
    private final int SCR_WIDTH = DeviceUtil.getScreenWidth();
    private final int SCR_HEIGHT = DeviceUtil.getScreenHeight() - DeviceUtil.getStatusHeight() - DeviceUtil.dp2px(55);

    /**********************************************************************************************/

    private FloatSub floatSub;
    private ViewDragHelper helper;

    /**********************************************************************************************/

    private SharedPreferences sp = getContext().getSharedPreferences(FloatLayout.class.getSimpleName(), Context.MODE_PRIVATE);
    private SharedPreferences.Editor editor = sp.edit();
    private final String FLOAT_SUB_TOP = "FLOAT_SUB_TOP";
    private final String FLOAT_SUB_LEFT = "FLOAT_SUB_LEFT";

    /**********************************************************************************************/

    public FloatLayout(Context context) {
        this(context, null, 0);
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLayout(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);

        floatSub = new FloatSub(APP.getInstance().getApplicationContext());
        floatSub.setVisibility(View.GONE);
        addView(floatSub);
        setFloatSubSize(DeviceUtil.dp2px(168), DeviceUtil.dp2px(100));

        helper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
//                Log.e(TAG, "tryCaptureView ==> ");
                return null == child ? false : child instanceof FloatSub;
            }

            /**
             * 手指触摸水平位移
             * @param child
             * @param left
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                Log.e(TAG, "clampViewPositionHorizontal ==> ");

//                final int leftBound = getPaddingLeft();
//                final int rightBound = getWidth() - child.getWidth() - leftBound;
//                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
//                return newLeft;

                return left;
            }

            /**
             * 手指触摸竖直位移
             * @param child
             * @param top
             * @param dy
             * @return
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                Log.e(TAG, "clampViewPositionVertical ==> ");

//                final int topBound = getPaddingTop();
//                final int bottomBound = getHeight() - child.getHeight() - dp2px(context, 50);
//                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
//                return newTop;

                return top;
            }

            /**
             * 水平方向可以滑动的距离
             * @param child
             * @return
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
//                Log.e(TAG, "getViewHorizontalDragRange ==> ");

//                return getMeasuredWidth() - child.getMeasuredWidth();
                return getMeasuredWidth();
            }

            /**
             * 竖直方向可以滑动的距离
             * @param child
             * @return
             */
            @Override
            public int getViewVerticalDragRange(View child) {
//                Log.e(TAG, "getViewVerticalDragRange ==> ");

//                return getMeasuredHeight() - child.getMeasuredHeight() - dp2px(context, 50);
                return getMeasuredHeight();
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);

                if (null == floatSub) return;
                // 更新坐标
                float x = floatSub.getX();
                float y = floatSub.getY();
                editor.putFloat(FLOAT_SUB_TOP, x);
                editor.putFloat(FLOAT_SUB_LEFT, y);
                editor.commit();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
//                Log.e(TAG, "onViewReleased ==> ");

                if (null == floatSub || releasedChild != floatSub) return;

                int width = releasedChild.getWidth();
                int height = releasedChild.getHeight();

                int left = releasedChild.getLeft();
                int top = releasedChild.getTop();
                int right = releasedChild.getRight();
                int bottom = releasedChild.getBottom();

                // 移到屏幕外边
                if (left < 0 || top < 0 || right > SCR_WIDTH || bottom > SCR_HEIGHT) {

                    // 左侧出边
                    if (left < 0) {
                        left = TEMP_SIZE;
                        if (top < 0) {
                            top = TEMP_SIZE;
                        }
                        if (bottom > SCR_HEIGHT) {
                            top = SCR_HEIGHT - height;
                        }
                    }
                    // 右侧出边
                    else if (right > SCR_WIDTH) {
                        int rightMax = SCR_WIDTH - TEMP_SIZE;
                        left = rightMax - width;
                        if (top < 0) {
                            top = TEMP_SIZE;
                        }
                        if (bottom > SCR_HEIGHT) {
                            top = SCR_HEIGHT - height;
                        }
                    }
                    // 上侧出边
                    else if (top < 0) {
                        top = TEMP_SIZE;
                    }
                    // 下侧出边
                    else if (bottom > SCR_HEIGHT) {
                        top = SCR_HEIGHT - height;
                    }
                }
                // 处于屏幕里面
                else {

                    // 距离左侧距离
                    int ragneLeft = left;
                    int ragneRight = SCR_WIDTH - right;
                    int ragneTop = top;
                    int rangeBottom = SCR_HEIGHT - bottom;

                    int temp1 = Math.min(ragneLeft, ragneRight);
                    int temp2 = Math.min(temp1, ragneTop);
                    int ragneMin = Math.min(temp2, rangeBottom);

                    if (ragneMin == ragneLeft) {
                        left = TEMP_SIZE;
                    } else if (ragneMin == ragneRight) {
                        int rightMax = SCR_WIDTH - TEMP_SIZE;
                        left = rightMax - width;
                    } else if (ragneMin == ragneTop) {
                        top = TEMP_SIZE;
                    } else {
                        top = SCR_HEIGHT - height;
                    }
                }

                helper.settleCapturedViewAt(left, top);
                invalidate();
            }
        });
    }

    /**********************************************************************************************/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (!helper.continueSettling(true)) return;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (null != floatSub && floatSub.getVisibility() == View.VISIBLE) {

            // 读取保存的位置
            float x = sp.getFloat(FLOAT_SUB_TOP, -1);
            float y = sp.getFloat(FLOAT_SUB_LEFT, -1);
//            LogUtil.e(TAG, "onLayout ==> x = " + x + ", y = " + y);

            if (x == -1 && y == -1) return;

            int measuredWidth = floatSub.getMeasuredWidth();
            int maxX = DeviceUtil.getScreenWidth() - measuredWidth - DeviceUtil.dp2px(5);
            if (x > maxX) {
                x = maxX;
                sp.edit().putFloat(FLOAT_SUB_TOP, x).commit();
            }
            int measuredHeight = floatSub.getMeasuredHeight();
            int maxY = DeviceUtil.getScreenHeight() - DeviceUtil.dp2px(80) - measuredHeight;
            if (y > maxY) {
                y = maxY;
                sp.edit().putFloat(FLOAT_SUB_LEFT, y).commit();
            }

            floatSub.layout((int) x, (int) y, (int) x + measuredWidth, (int) y + measuredHeight);
        }
    }

    /**********************************************************************************************/

    public void clearData() {
        if (null == sp) return;
        sp.edit().clear().commit();
    }

    public FloatSub getFloatSub() {
        return floatSub;
    }

    public void setFloatSubSize(int width, int height) {
        if (null == floatSub) return;

        LayoutParams params = (LayoutParams) floatSub.getLayoutParams();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.topMargin = DeviceUtil.dp2px(170);
        params.rightMargin = DeviceUtil.dp2px(5);
    }

    public void addFloatSub(View view) {
        if (null == floatSub) return;
        floatSub.addView(view, 0);
        floatSub.setVisibility(View.VISIBLE);
    }

    public void setOnFloatSubClickListener(OnFloatClickListener listener) {
        if (null == floatSub) return;
        floatSub.setOnFloatClickListener(listener);
    }

    /**********************************************************************************************/
}