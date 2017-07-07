package com.mobile.love.enjoy.consume.custom.toast;


import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by chenaxing on 2017/1/17.
 */

public class BaseToastManager extends Handler
{
    private static final class Messages
    {

        /* Hexadecimal numbers that represent acronyms for the operation * */
        private static final int DISPLAY_BASETOAST = 0x445354;
        private static final int ADD_BASETOAST = 0x415354;
        private static final int REMOVE_BASETOAST = 0x525354;
    }

    private static BaseToastManager mBaseToastManager;

    private final Queue<BaseToast> mQueue;

    private BaseToastManager()
    {
        mQueue = new LinkedBlockingDeque<BaseToast>();
    }

    protected static synchronized BaseToastManager getInstance()
    {
        if(mBaseToastManager == null)
        {
            mBaseToastManager = new BaseToastManager();
        }
        return mBaseToastManager;
    }

    public void addToast(BaseToast baseToast)
    {
        mQueue.add(baseToast);
        showNextBaseToast();
    }

    private void showNextBaseToast()
    {
        if(mQueue.isEmpty())
        {
            return;
        }
        final BaseToast toast = mQueue.peek();
        if(!toast.isShow())
        {
            Message msg = obtainMessage(Messages.ADD_BASETOAST);
            msg.obj = toast;
            sendMessage(msg);
        }
        else
        {
            sendMessageDelayed(toast, Messages.DISPLAY_BASETOAST, getDuration(toast));
        }
    }

    private void displayBaseToast(BaseToast baseToast)
    {
        if(baseToast.isShow())
        {
            return;
        }
        final WindowManager windowManager = baseToast.getWindowManager();
        final View toastView = baseToast.getToastView();
        final WindowManager.LayoutParams layoutParams = baseToast.getWindowManagerLayoutParams();

        if(windowManager != null)
        {
            windowManager.addView(toastView, layoutParams);
        }
        sendMessageDelayed(baseToast, Messages.REMOVE_BASETOAST, baseToast.getDuration() + 500);
    }

    protected void removeBaseToast(BaseToast baseToast)
    {
        final WindowManager windowManager = baseToast.getWindowManager();
        final View toastView = baseToast.getToastView();
        if(windowManager != null)
        {
            mQueue.poll();
            windowManager.removeView(toastView);
            sendMessageDelayed(baseToast, Messages.DISPLAY_BASETOAST, 500);
        }
    }


    private void sendMessageDelayed(BaseToast baseToast, final int messageId, final long delayed)
    {
        Message msg = obtainMessage(messageId);
        msg.obj = baseToast;
        sendMessageDelayed(msg, delayed);
    }

    @Override
    public void handleMessage(Message msg)
    {
        final BaseToast baseToast = (BaseToast) msg.obj;
        switch(msg.what)
        {
            case Messages.ADD_BASETOAST:
                displayBaseToast(baseToast);
                break;
            case Messages.DISPLAY_BASETOAST:
                showNextBaseToast();
                break;
            case Messages.REMOVE_BASETOAST:
                removeBaseToast(baseToast);
                break;
            default:
                super.handleMessage(msg);
                break;
        }
    }

    /* Get duration and add one second to compensate for show/hide animations */
    private long getDuration(BaseToast superToast)
    {

        long duration = superToast.getDuration();
        duration += 1000;

        return duration;
    }

    protected void removeAllBaseToast()
    {
        removeMessages(Messages.ADD_BASETOAST);
        removeMessages(Messages.DISPLAY_BASETOAST);
        removeMessages(Messages.REMOVE_BASETOAST);

        for(BaseToast baseToast : mQueue)
        {
            if(baseToast.isShow())
            {
                baseToast.getWindowManager().removeView(baseToast.getToastView());
            }
        }
        mQueue.clear();
    }
}
