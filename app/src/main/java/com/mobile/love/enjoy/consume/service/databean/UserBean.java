package com.mobile.love.enjoy.consume.service.databean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mobile.love.enjoy.consume.service.TableConstants;

/**
 * Created by chenaxing on 2017/1/17.
 */

@DatabaseTable(tableName = TableConstants.TABLEUSER)
public class UserBean
{
    @DatabaseField(generatedId = true, columnName = TableConstants.KEYID)
    public int keyId;

    @DatabaseField(columnName = TableConstants.USERID)
    public String userId;

    @DatabaseField(columnName = TableConstants.USERNAME)
    public String userName;

    @DatabaseField(columnName = TableConstants.USERPWD)
    public String userPwd;

    @DatabaseField(canBeNull = true, columnName = TableConstants.USERSEX)
    public int userSex;

    @DatabaseField(columnName = TableConstants.USERDESC, defaultValue = "这家伙很懒，什么都没有留下")
    public String userDesc;

    @DatabaseField(columnName = TableConstants.USERLEVEL, defaultValue = "1.0")
    public float userLevel;

    @DatabaseField(columnName = TableConstants.USERLEVELSTAR, defaultValue = "1.0")
    public float userLevelStar;
}
