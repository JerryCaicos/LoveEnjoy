package com.mobile.love.enjoy.consume.service;

import android.content.Context;

/**
 * Created by chenaxing on 2017/1/16.
 */

public interface BasicService
{
    String DEVICE_INFO_SERVICE = "device_info_service";

    String NETWORK_INFO_SERVICE = "network_info_service";

    String DATABASE_INIT_SERVICE = "database_init_service";

    void onApplicationCreated(Context context);

    void onApplicationDestory(Context context);
}
