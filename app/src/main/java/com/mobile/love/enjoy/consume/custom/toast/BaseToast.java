package com.mobile.love.enjoy.consume.custom.toast;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.R;

/**
 * Created by chenaxing on 2017/1/17.
 */

public class BaseToast
{
    public static final String TAG = "BaseToast";

    public enum Animation
    {
        FADE,
        FLYIN,
        SCALE,
        POPUP
    }

    public static class Duration
    {
        public static final int VERYSHOT = (1500);
        public static final int SHOT = (2000);
        public static final int MEDIUM = (2500);
        public static final int LONG = (3000);
        public static final int EXTRA_LONG = (5000);
    }

    private Animation mAnimation = Animation.SCALE;

    private int mGravity = Gravity.BOTTOM | Gravity.CENTER;

    private int mDuration = Duration.MEDIUM;

    private int mXOffset = 0;

    private int mYOffset = 0;

    private TextView mMessageTextView;

    private View mToastView;

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mWindowManagerLayoutParams;

    private BaseToast(Context context)
    {
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mToastView = inflater.inflate(R.layout.view_base_toast_layout, null);
        mMessageTextView = (TextView) mToastView.findViewById(R.id.view_base_toast);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mYOffset = dm.heightPixels>>2;
    }

    public void show()
    {
        mWindowManagerLayoutParams = new WindowManager.LayoutParams();
        mWindowManagerLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowManagerLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowManagerLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mWindowManagerLayoutParams.format = PixelFormat.TRANSLUCENT;
        mWindowManagerLayoutParams.windowAnimations = getAnimation();
        mWindowManagerLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mWindowManagerLayoutParams.gravity = mGravity;
        mWindowManagerLayoutParams.x = mXOffset;
        mWindowManagerLayoutParams.y = mYOffset;

        BaseToastManager.getInstance().addToast(this);

    }

    private void setToastMsg(CharSequence msg)
    {
        mMessageTextView.setText(msg);
    }

    private void setDuratioin(int duratioin)
    {
        if(duratioin>Duration.EXTRA_LONG)
        {
            this.mDuration = Duration.EXTRA_LONG;
        }
        this.mDuration = duratioin;
    }

    public WindowManager getWindowManager()
    {
        return mWindowManager;
    }

    public WindowManager.LayoutParams getWindowManagerLayoutParams()
    {
        return mWindowManagerLayoutParams;
    }

    public View getToastView()
    {
        return mToastView;
    }

    public int getDuration()
    {
        return mDuration;
    }


    public boolean isShow()
    {
        return mToastView != null && mToastView.isShown();
    }

    private int getAnimation()
    {
        if(mAnimation == Animation.FLYIN)
        {
            return android.R.style.Animation_Translucent;
        }
        else if(mAnimation == Animation.SCALE)
        {
            return android.R.style.Animation_Dialog;
        }
        else if(mAnimation == Animation.POPUP)
        {
            return android.R.style.Animation_InputMethod;
        }
        else
        {
            return android.R.style.Animation_Toast;
        }
    }

    public static BaseToast create(Context context, String msg, int duratioin)
    {
        BaseToast baseToast = new BaseToast(context);
        baseToast.setDuratioin(duratioin);
        baseToast.setToastMsg(msg);
        return baseToast;
    }

    public void cancelAllToasts()
    {
        BaseToastManager.getInstance().removeAllBaseToast();
    }

    public void dismissToast()
    {
        BaseToastManager.getInstance().removeBaseToast(this);
    }

}
