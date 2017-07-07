package com.mobile.love.enjoy.consume.service;

import android.content.Context;
import android.provider.ContactsContract;

import com.mobile.love.enjoy.consume.service.databean.CategoryBean;
import com.mobile.love.enjoy.consume.service.databean.UserBean;
import com.mobile.love.enjoy.consume.utils.LogUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenaxing on 2017/1/16.
 */

public class DBHelper<T>
{
    private static DBHelper mDBHelper;

    private Context mContext;

    private DataBaseHelper mDataBaseHelper;

    private DBHelper(Context context)
    {
        mContext = context;
        mDataBaseHelper = DataBaseHelper.getInstance(context);
    }

    public static DBHelper getInstance(Context context)
    {
        if(mDBHelper == null)
        {
            mDBHelper = new DBHelper(context);
        }
        return mDBHelper;
    }

    public void insertBean(Class<T> clazz, T data)
    {
        try
        {
            mDataBaseHelper.getBeanDao(clazz).create(data);
        }
        catch(SQLException e)
        {
            LogUtils.e(this, e);
        }
    }

    public void updateBean(Class<T> clazz, T data)
    {
        try
        {
            mDataBaseHelper.getBeanDao(clazz).update(data);
        }
        catch(SQLException e)
        {
            LogUtils.e(this, e);
        }
    }

    public void deleteBean(Class<T> clazz, T data)
    {
        try
        {
            mDataBaseHelper.getBeanDao(clazz).delete(data);
        }
        catch(SQLException e)
        {
            LogUtils.e(this, e);
        }
    }

    public List<T> queryBeans(Class<T> clazz)
    {
        List<T> list = new ArrayList<>();
        try
        {
            list.addAll(mDataBaseHelper.getBeanDao(clazz).queryForAll());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public List<T> queryObjectBean(Class<T> clazz, Map<String, String> map)
    {
        List<T> list = new ArrayList<>();
        try
        {
            list.addAll(mDataBaseHelper.getBeanDao(clazz).queryForFieldValuesArgs(map));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
