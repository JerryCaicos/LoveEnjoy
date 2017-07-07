package com.mobile.love.enjoy.consume.service.databean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mobile.love.enjoy.consume.service.TableConstants;

import java.sql.Timestamp;

/**
 * Created by JerryCaicos on 2017/1/17.
 */

@DatabaseTable(tableName = TableConstants.TABLEORDER)
public class OrderBean
{
    @DatabaseField(generatedId = true, columnName = TableConstants.KEYID)
    public int keyId;

    @DatabaseField(canBeNull = false,columnName = TableConstants.USERID)
    public int userId;

    @DatabaseField(canBeNull = false,columnName = TableConstants.ORDERID)
    public String orderId;

    @DatabaseField(canBeNull = false,columnName = TableConstants.ORDERTIME)
    public Timestamp orderTime;

    @DatabaseField(canBeNull = false,columnName = TableConstants.ORDERTYPE)
    public String orderType;

    @DatabaseField(canBeNull = false,columnName = TableConstants.ORDERPRICE)
    public double orderPrice;

    @DatabaseField(columnName = TableConstants.ORDERDESC)
    public String orderDesc;

    @DatabaseField(columnName = TableConstants.ORDERADDRESS)
    public String orderAddress;

    @DatabaseField(columnName = TableConstants.ORDERCATEGROYID)
    public String orderCategoryId;
}
