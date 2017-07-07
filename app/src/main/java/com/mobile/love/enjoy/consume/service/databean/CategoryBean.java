package com.mobile.love.enjoy.consume.service.databean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mobile.love.enjoy.consume.service.TableConstants;

/**
 * Created by chenaxing on 2017/1/17.
 */

@DatabaseTable(tableName = TableConstants.TABLECATEGORY)
public class CategoryBean
{
    @DatabaseField(generatedId = true,columnName = TableConstants.KEYID)
    public int keyId;

    @DatabaseField(canBeNull = false,columnName = TableConstants.CATEGORYID)
    public String categoryId;

    @DatabaseField(canBeNull = false,columnName = TableConstants.CATEGORYNAME)
    public String categoryName;
}
