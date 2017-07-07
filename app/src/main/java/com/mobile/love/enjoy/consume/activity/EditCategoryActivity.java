package com.mobile.love.enjoy.consume.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.mobile.love.enjoy.consume.BaseActivity;
import com.mobile.love.enjoy.consume.R;
import com.mobile.love.enjoy.consume.custom.sideslide.SlideBaseAdapter;
import com.mobile.love.enjoy.consume.custom.sideslide.SlideListView;
import com.mobile.love.enjoy.consume.service.DBHelper;
import com.mobile.love.enjoy.consume.service.TableConstants;
import com.mobile.love.enjoy.consume.service.databean.CategoryBean;

import java.text.DecimalFormat;
import java.util.List;

public class EditCategoryActivity extends BaseActivity
{
    public static final int MSG_CATEGORY_TYPE_EIDT = 10000;
    public static final int MSG_CATEGORY_TYPE_DELETE = 10001;

    private ViewHolder viewHolder;

    private SlideBaseAdapter slideBaseAdapter;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case MSG_CATEGORY_TYPE_EIDT:
                    editCategory((CategoryBean) msg.obj);
                    break;
                case MSG_CATEGORY_TYPE_DELETE:
                    deleteCategory((CategoryBean) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };


    private void editCategory(CategoryBean categoryBean)
    {
        showLoadingView(true);

        DBHelper<CategoryBean> dbHelper = DBHelper.getInstance(this);
        dbHelper.updateBean(CategoryBean.class, categoryBean);

        hideLoadingView();
    }

    private void deleteCategory(CategoryBean categoryBean)
    {
        showLoadingView(true);

        switch(categoryBean.categoryId)
        {
            case TableConstants.CATEGORY_TYPE_BEAUTY:
            case TableConstants.CATEGORY_TYPE_DINNER:
            case TableConstants.CATEGORY_TYPE_HAPPY:
            case TableConstants.CATEGORY_TYPE_MEDICAL:
            case TableConstants.CATEGORY_TYPE_TRAFFIC:
            case TableConstants.CATEGORY_TYPE_USUAL_FEAST:
            case TableConstants.CATEGORY_TYPE_USUAL_LIFE:
            case TableConstants.CATEGORY_TYPE_WEAR:
            case TableConstants.CATEGORY_TYPE_OTHER:
                showToastMsg(R.string.string_category_delete_cant_hint);
                hideLoadingView();
                return;
            default:
                break;
        }

        DBHelper<CategoryBean> dbHelper = DBHelper.getInstance(this);
        dbHelper.deleteBean(CategoryBean.class, categoryBean);

        hideLoadingView();

        showToastMsg("");
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewHolder = new ViewHolder();
        setContentView(R.layout.activity_category_edit, true);
        setHeaderTitle(R.string.string_category_activity_title);
        addTextAction(R.string.string_category_activity_action_add, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
        viewHolder.slideListView = (SlideListView) findViewById(R.id.view_category_list);
        getCategories();
    }

    private void getCategories()
    {
        DBHelper<CategoryBean> dbHelper = DBHelper.getInstance(this);
        List<CategoryBean> categoryBeanList = dbHelper.queryBeans(CategoryBean.class);
        if(categoryBeanList.isEmpty())
        {
            initCategoryData();
            categoryBeanList = dbHelper.queryBeans(CategoryBean.class);
        }
        slideBaseAdapter = new SlideBaseAdapter(this, mHandler);
        slideBaseAdapter.setDataList(categoryBeanList);
        viewHolder.slideListView.setAdapter(slideBaseAdapter);
    }

    class ViewHolder
    {
        SlideListView slideListView;
    }
}
