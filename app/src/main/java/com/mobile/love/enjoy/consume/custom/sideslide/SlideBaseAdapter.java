package com.mobile.love.enjoy.consume.custom.sideslide;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.R;
import com.mobile.love.enjoy.consume.activity.EditCategoryActivity;
import com.mobile.love.enjoy.consume.service.TableConstants;
import com.mobile.love.enjoy.consume.service.databean.CategoryBean;

import java.util.List;

/**
 * Created by JerryCaicos on 2017/3/3.
 */

public class SlideBaseAdapter extends BaseAdapter
{
    protected Context mContext;

    protected Handler mHandler;

    protected List<CategoryBean> dataList;

    protected LayoutInflater inflater;

    protected ViewHolder viewHolder;

    public SlideBaseAdapter(Context context, Handler handler)
    {
        mContext = context;
        mHandler = handler;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<CategoryBean> list)
    {
        dataList = list;
    }

    @Override
    public int getCount()
    {
        return dataList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        viewHolder = null;
        if(convertView == null)
        {
            View menuView = inflater.inflate(R.layout.view_category_item_menu, null);
            View contentView = inflater.inflate(R.layout.view_category_item_content, null);

            viewHolder = new ViewHolder();
            SlideItemView slideItemView = new SlideItemView(mContext);
            slideItemView.setSlideItemView(contentView, menuView);
            viewHolder.viewItemIcon = (ImageView) contentView.findViewById(R.id.view_category_item_icon);
            viewHolder.viewItemContent = (TextView) contentView.findViewById(R.id.view_category_item_hint);
            viewHolder.viewItemEditBtn = (TextView) menuView.findViewById(R.id.view_category_item_edit);
            viewHolder.viewItemDeleteBtn = (TextView) menuView.findViewById(R.id.view_category_item_delete);
            convertView = slideItemView;
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.viewItemContent.setText(dataList.get(position).categoryName);

        String icon = dataList.get(position).categoryId;
        switch(icon)
        {
            case TableConstants.CATEGORY_TYPE_BEAUTY:
                /**美妆**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_beauty));
                break;
            case TableConstants.CATEGORY_TYPE_DINNER:
                /**宴会**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_dinner));
                break;
            case TableConstants.CATEGORY_TYPE_HAPPY:
                /**娱乐**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_happy));
                break;
            case TableConstants.CATEGORY_TYPE_MEDICAL:
                /**医疗**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_medical));
                break;
            case TableConstants.CATEGORY_TYPE_TRAFFIC:
                /**出行**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_traffic));
                break;
            case TableConstants.CATEGORY_TYPE_WEAR:
                /**穿衣**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_wear));
                break;
            case TableConstants.CATEGORY_TYPE_USUAL_FEAST:
                /**日常餐饮**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_usual_feast));
                break;
            case TableConstants.CATEGORY_TYPE_USUAL_LIFE:
                /**生活日用**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_usual_life));
                break;
            case TableConstants.CATEGORY_TYPE_OTHER:
            default:
                /**其他**/
                viewHolder.viewItemIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.icon_category_other));
                break;
        }

        final SlideItemView itemView = (SlideItemView) convertView;

        viewHolder.viewItemEditBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Message message = mHandler.obtainMessage();
                message.obj = dataList.get(position);
                message.what = EditCategoryActivity.MSG_CATEGORY_TYPE_EIDT;
                mHandler.sendMessage(message);
                itemView.autoCloseMenu();
            }
        });
        viewHolder.viewItemDeleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Message message = mHandler.obtainMessage();
                message.obj = dataList.get(position);
                message.what = EditCategoryActivity.MSG_CATEGORY_TYPE_DELETE;
                mHandler.sendMessage(message);
                itemView.autoCloseMenu();
            }
        });

        return convertView;
    }

    class ViewHolder
    {
        ImageView viewItemIcon;
        TextView viewItemContent;
        TextView viewItemEditBtn;
        TextView viewItemDeleteBtn;
    }


}
