package com.mobile.love.enjoy.consume.custom.sideslide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.mobile.love.enjoy.consume.R;

/**
 * Created by chenaxing on 2017/3/3.
 */

public class SlideItemView extends LinearLayout
{
    /**
     * 侧滑菜单状态
     **/
    private int status = SlideConstants.STATUS_CLOSE;
    /**
     * 父布局偏移位置
     **/
    private int positionOffset;
    /**
     * 手指按下的位置
     **/
    private float downPosition;

    private int offset;
    /**
     * 正常显示的view
     **/
    private View contentView;
    /**
     * 侧滑显示的view
     **/
    private View menuView;
    /**
     * 计算滑动动画效果
     **/
    private OverScroller openScroller;
    /**
     * 计算滑动动画效果
     **/
    private OverScroller closeScroller;

    private Context mContext;

    public SlideItemView(Context context)
    {
        super(context);
        mContext = context;
    }

    public SlideItemView(Context context, AttributeSet attr)
    {
        super(context, attr);
        mContext = context;
    }

    public SlideItemView(Context context, AttributeSet attr, int defStyleAttr)
    {
        super(context, attr, defStyleAttr);
        mContext = context;
    }


    public void setSlideItemView(View content, View menu)
    {
        contentView = content;
        menuView = menu;

        openScroller = new OverScroller(mContext);
        closeScroller = new OverScroller(mContext);

        setOrientation(LinearLayout.HORIZONTAL);

        initView();
    }

    private void initView()
    {
        //这是设置宽和高
        LayoutParams contentParams = new LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LayoutParams rightParams=new LayoutParams
                (LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(contentParams);
        menuView.setLayoutParams(rightParams);
        this.addView(contentView);
        this.addView(menuView);
    }

    public boolean isMenuOpen()
    {
        return status == SlideConstants.STATUS_OPEN;
    }

    public boolean onSlide(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                offset = 0;
                downPosition = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                offset = (int) (downPosition - event.getX());
                if(status == SlideConstants.STATUS_OPEN)
                {
                    offset += menuView.getMeasuredWidth();
                }
                move(offset);
                break;
            case MotionEvent.ACTION_UP:
                if(offset > menuView.getMeasuredWidth() / 3)
                {
                    autoOpenMenu();
                }
                else
                {
                    autoCloseMenu();
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * 移动视图
     *
     * @param position 移动的偏移量
     */
    private void move(int position)
    {
        /**如果移动偏移量超过了右侧菜单的宽度，最多滑动到菜单宽度**/
        if(position > menuView.getMeasuredWidth())
        {
            position = menuView.getMeasuredWidth();
        }
        /**如果移动偏移量小于0，偏移量置为0**/
        if(position < 0)
        {
            position = 0;
        }

        contentView.layout(- position, contentView.getTop(),
                contentView.getWidth() - position, contentView.getBottom());
        menuView.layout(contentView.getWidth() - position, menuView.getTop(),
                contentView.getWidth() + menuView.getWidth(), menuView.getBottom());
    }

    @Override
    public void computeScroll()
    {
        if(status == SlideConstants.STATUS_OPEN)
        {
            /**滑动是否结束**/
            if(openScroller.computeScrollOffset())
            {
                move(openScroller.getCurrX());
                postInvalidate();
            }
        }
        else
        {
            if(closeScroller.computeScrollOffset())
            {
                move(positionOffset - closeScroller.getCurrX());
                postInvalidate();
            }
        }
    }

    /**
     * 自动打开菜单
     */
    public void autoOpenMenu()
    {
        status = SlideConstants.STATUS_OPEN;
        openScroller.startScroll(- contentView.getLeft(), 0, menuView.getMeasuredWidth(), 0, 300);
        postInvalidate();
    }

    /**
     * 自动关闭菜单
     */
    public void autoCloseMenu()
    {
        status = SlideConstants.STATUS_CLOSE;
        positionOffset = - contentView.getLeft();
        closeScroller.startScroll(0, 0, positionOffset, 0, 300);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(menuView != null)
        {
            menuView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        if(contentView != null)
        {
            contentView.layout(0, 0, getMeasuredWidth(), contentView.getMeasuredHeight());
        }
        if(menuView != null)
        {
            menuView.layout(getMeasuredWidth(), 0,
                    getMeasuredWidth() + menuView.getWidth(), contentView.getMeasuredHeight());
        }
    }
}
