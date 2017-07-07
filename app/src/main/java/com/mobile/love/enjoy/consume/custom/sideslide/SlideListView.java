package com.mobile.love.enjoy.consume.custom.sideslide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by chenaxing on 2017/3/3.
 */

public class SlideListView extends ListView
{
    /**
     * 选中的item的position
     **/
    private int touchPosition = -1;
    /**
     * 滑动状态，横向滑动还是纵向滑动
     **/
    private int touchStatus;
    /**
     * 手指按下的横坐标
     **/
    private int downX;
    /**
     * 手指按下的纵坐标
     **/
    private int downY;
    /**
     * 选中的item
     **/
    private SlideItemView touchedView;

    public SlideListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public View getFocusedChild()
    {
        return super.getFocusedChild();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                /**记录手指按下的x坐标**/
                downX = (int) event.getX();
                /**记录手指按下的y坐标**/
                downY = (int) event.getY();
                /**滑动状态，横向滑动还是纵向滑动**/
                touchStatus = SlideConstants.STATUS_SLIDE_NONE;
                /**根据手指按下的位置获取选中的item的position**/
                touchPosition = pointToPosition(downX, downY);

                /**如果当前选中的item是打开状态，则关闭**/
                if(touchedView != null
                        && touchedView.isMenuOpen())
                {
                    touchedView.autoCloseMenu();
                    touchedView = null;
                    return true;
                }

                View currentView = getChildAt(touchPosition - getFirstVisiblePosition());

                /**判断选中的item是不是该类型**/
                if(currentView instanceof SlideItemView)
                {
                    touchedView = (SlideItemView) currentView;
                    touchedView.onSlide(event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /**计算纵向滑动的距离**/
                float yOffset = Math.abs(event.getY() - downY);
                float xOffset = Math.abs(event.getX() - downX);

                /**如果不是横向滑动，根据纵向滑动距离，设置滑动状态**/
                if(xOffset > yOffset * 1.2)
                {
                    touchStatus = SlideConstants.STATUS_SLIDE_HORIZONTAL;
                }
                else
                {
                    touchStatus = SlideConstants.STATUS_SLIDE_VERTICAL;
                }

                if(touchStatus == SlideConstants.STATUS_SLIDE_HORIZONTAL && touchedView != null)
                {
                    touchedView.onSlide(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                /**只有横向滑动的时候，处理事件，纵向滑动不处理，向上传递**/
                if(touchStatus == SlideConstants.STATUS_SLIDE_HORIZONTAL)
                {
                    if(touchedView != null)
                    {
                        touchedView.onSlide(event);
                    }
                    return true;
                }
                else
                {
                    if(touchedView != null)
                    {
                        touchedView.autoCloseMenu();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
