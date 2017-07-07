package com.mobile.love.enjoy.consume;

import android.app.Application;
import android.content.Context;

import com.mobile.love.enjoy.consume.service.DBHelper;
import com.mobile.love.enjoy.consume.service.databean.CategoryBean;
import com.mobile.love.enjoy.consume.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chenaxing on 2017/1/17.
 */

public class BaseApplication extends Application
{
    private static BaseApplication application;

    public static BaseApplication getInstance()
    {
        return application;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        LogUtils.d("BaseApplication onCreated");
        application = this;

        initCategoryData();
    }

    private void initCategoryData()
    {
        DBHelper<CategoryBean> dbHelper = DBHelper.getInstance(this);
        List<CategoryBean> list = dbHelper.queryBeans(CategoryBean.class);
        if(list.isEmpty())
        {
            String[] strs = getResources().getStringArray(R.array.order_category_type);
            List<String> categories = Arrays.asList(strs);
            int size = categories.size();
            CategoryBean category = null;
            for(int i = 0; i < size; i++)
            {
                category = new CategoryBean();
                category.categoryId = String.valueOf(i + 1);
                category.categoryName = categories.get(i);
                list.add(category);
            }

            if(!list.isEmpty())
            {
                int len = list.size();
                for(int i = 0; i < len; i++)
                {
                    dbHelper.insertBean(CategoryBean.class,list.get(i));
                }
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
    }

    public void exit()
    {
        /**1、关闭所有的service**/
        /**2、关闭数据库**/
        /**3、结束当前进程**/
        System.exit(0);
    }

    public void destory()
    {
        /**1、关闭所有的service**/
        /**2、关闭数据库**/
        /**3、结束当前进程**/
        System.exit(0);
    }
}
