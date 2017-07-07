package com.mobile.love.enjoy.consume.custom.header;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.R;

/**
 * Created by chenaxing on 2017/2/21.
 */

/**
 * 界面通用头部, 样式如下:
 * <PRE>
 * ________________________________________________
 * |                                                |
 * | ＜-           TitleView      action1   action2  |
 * |________________________________________________|
 * ↑              ↑               ↑        ↑
 * BackAction     TitleView      action    action
 * </PRE>
 * <B>左边是固定的</B>,中间/右边的action是可以动态修改的.
 */
public class HeaderBuilder
{
    private ViewGroup mHeaderView;

    private Context mContext;

    public HeaderBuilder(Context context)
    {
        mContext = context;
        mHeaderView = (ViewGroup) LayoutInflater.from(mContext)
                .inflate(R.layout.view_base_activity_title_bar, null);
    }

    public void setBackActionListener(View.OnClickListener listener)
    {
        View backBtn = mHeaderView.findViewById(R.id.view_title_btn_back);
        backBtn.setOnClickListener(listener);
    }

    public void setBackActionVisibility(int visibility)
    {
        View backBtn = mHeaderView.findViewById(R.id.view_title_btn_back);
        backBtn.setVisibility(visibility);
    }

    public void setHeaderTitle(int resId)
    {
        setHeaderTitle(mContext.getText(resId));
    }

    public void setHeaderTitle(CharSequence title)
    {
        TextView titleView = (TextView) mHeaderView.findViewById(R.id.view_title_content_middle);
        titleView.setVisibility(View.VISIBLE);
        mHeaderView.findViewById(R.id.layout_title_content_middle).setVisibility(View.GONE);
        titleView.setText(title);
    }

    public void setHeaderTitleVisibility(int visibility)
    {
        LinearLayout container = (LinearLayout) mHeaderView
                .findViewById(R.id.layout_title_content_middle);
        container.setVisibility(visibility);
    }

    public void setHeaderActionVisibility(int visibility)
    {
        LinearLayout container = (LinearLayout) mHeaderView
                .findViewById(R.id.layout_title_action_bar_right);
        container.setVisibility(visibility);
    }

    public void setHeaderTitle(View view)
    {
        LinearLayout container = (LinearLayout) mHeaderView
                .findViewById(R.id.layout_title_content_middle);
        container.setVisibility(View.VISIBLE);
        mHeaderView.findViewById(R.id.view_title_content_middle).setVisibility(View.GONE);
        container.removeAllViews();
        container.addView(view);
    }

    public void setTextAction(int resId, View.OnClickListener listener)
    {
        LinearLayout actionContainer = (LinearLayout) mHeaderView
                .findViewById(R.id.layout_title_action_bar_right);
        TextView actionView = (TextView) LayoutInflater.from(mContext)
                .inflate(R.layout.view_base_activity_title_action_text, null);
        actionView.setText(mContext.getText(resId));
        actionView.setOnClickListener(listener);
        actionContainer.addView(actionView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setTextAction(CharSequence name, View.OnClickListener listener)
    {
        LinearLayout actionContainer = (LinearLayout) mHeaderView
                .findViewById(R.id.layout_title_action_bar_right);
        TextView actionView = (TextView) LayoutInflater.from(mContext)
                .inflate(R.layout.view_base_activity_title_action_text, null);
        actionView.setText(name);
        actionView.setOnClickListener(listener);
        actionContainer.addView(actionView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setIconAction(Drawable drawable, View.OnClickListener listener)
    {
        LinearLayout actionContainer = (LinearLayout) mHeaderView
                .findViewById(R.id.layout_title_action_bar_right);
        ImageView actionView = (ImageView) LayoutInflater.from(mContext)
                .inflate(R.layout.view_base_activity_title_action_icon, null);
        actionView.setImageDrawable(drawable);
        actionView.setOnClickListener(listener);
        actionContainer.addView(actionView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setIconAction(int resId, View.OnClickListener listener)
    {
        LinearLayout actionContainer = (LinearLayout) mHeaderView
                .findViewById(R.id.layout_title_action_bar_right);
        ImageView actionView = (ImageView) LayoutInflater.from(mContext)
                .inflate(R.layout.view_base_activity_title_action_icon, null);
        actionView.setImageDrawable(mContext.getResources().getDrawable(resId));
        actionView.setOnClickListener(listener);
        actionContainer.addView(actionView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public View getHeaderView()
    {
        return mHeaderView;
    }
}
