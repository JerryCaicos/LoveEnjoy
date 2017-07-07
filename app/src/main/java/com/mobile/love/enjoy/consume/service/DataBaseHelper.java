package com.mobile.love.enjoy.consume.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mobile.love.enjoy.consume.service.databean.CategoryBean;
import com.mobile.love.enjoy.consume.service.databean.OrderBean;
import com.mobile.love.enjoy.consume.service.databean.UserBean;
import com.mobile.love.enjoy.consume.utils.LogUtils;

import java.sql.SQLException;

/**
 * Created by chenaxing on 2017/1/16.
 */

public class DataBaseHelper<T, ID> extends OrmLiteSqliteOpenHelper
{
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "loveEnjoy.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static int DATABASE_VERSION = 1;

    public static DataBaseHelper mDataBaseHelper;

    public Dao<T, ID> beanDao;

    private DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static DataBaseHelper getInstance(Context context)
    {
        if(mDataBaseHelper == null)
        {
            synchronized(DataBaseHelper.class)
            {
                mDataBaseHelper = new DataBaseHelper(context);
            }
        }
        return mDataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTableIfNotExists(connectionSource, UserBean.class);
            TableUtils.createTableIfNotExists(connectionSource, CategoryBean.class);
            TableUtils.createTableIfNotExists(connectionSource, OrderBean.class);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try
        {
            TableUtils.dropTable(connectionSource, UserBean.class, true);
            TableUtils.dropTable(connectionSource, CategoryBean.class, true);
            TableUtils.dropTable(connectionSource, OrderBean.class, true);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        onCreate(database, connectionSource);
    }

    public Dao<T, ID> getBeanDao(Class<T> clazz)
    {
        try
        {
            beanDao = getDao(clazz);
        }
        catch(SQLException e)
        {
            LogUtils.e(this, e);
        }
        return beanDao;
    }

    @Override
    public void close()
    {
        super.close();
        beanDao = null;
        mDataBaseHelper = null;
    }
}
