package com.mobile.love.enjoy.consume.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.BaseActivity;
import com.mobile.love.enjoy.consume.R;
import com.mobile.love.enjoy.consume.service.DBHelper;
import com.mobile.love.enjoy.consume.service.TableConstants;
import com.mobile.love.enjoy.consume.service.databean.UserBean;
import com.mobile.love.enjoy.consume.utils.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLoginActivity extends BaseActivity
{
    private ViewHolder viewHolder;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewHolder = new ViewHolder();
        setContentView(R.layout.activity_user_login, true);
        initActivity();
        setHeaderTitle(R.string.string_user_login_hint_activity);
        addTextAction(getText(R.string.string_title_action_text_register), new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoRegister("", "");
            }
        });
    }

    private void initActivity()
    {
        viewHolder.viewUserName = (EditText) findViewById(R.id.view_login_user_name);
        viewHolder.viewUserPwd = (EditText) findViewById(R.id.view_login_user_pwd);
        viewHolder.viewBtnLogin = (TextView) findViewById(R.id.view_login_user_login_btn);
        viewHolder.viewBtnLogin.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch(view.getId())
            {
                case R.id.view_login_user_login_btn:
                    doUserLogin();
                    break;
                default:
                    break;
            }
        }
    };

    private void gotoRegister(String name, String pwd)
    {
        Intent intent = new Intent();
        intent.setClass(this, UserRegisterActivity.class);
        intent.putExtra(UserRegisterActivity.KEY_REGISTER_ID, name);
        intent.putExtra(UserRegisterActivity.KEY_REGISTER_PWD, pwd);
        startActivity(intent);
    }

    private void doUserLogin()
    {
        String name = viewHolder.viewUserName.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            showToastMsg(R.string.string_login_right_user_name);
            return;
        }
        String pwd = viewHolder.viewUserPwd.getText().toString();
        if(TextUtils.isEmpty(pwd))
        {
            showToastMsg(R.string.string_login_right_user_pwd);
            return;
        }
        showLoadingView(false);
        Map<String, String> map = new HashMap<>();
        map.put(TableConstants.USERNAME, name);
        map.put(TableConstants.USERPWD, pwd);
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        List<UserBean> users = dbHelper.queryObjectBean(UserBean.class, map);
        LogUtils.d(TAG, "users table's size = " + users.size());
        hideLoadingView();
        if(users != null && users.size()>0)
        {
            showToastMsg(R.string.string_login_login_success);
            // TODO: 2017/2/22
        }
        else
        {
            leadUserToRegister(name, pwd);
        }
    }

    private void leadUserToRegister(final String name, final String pwd)
    {
        showDialog(null, getString(R.string.string_login_new_user), getString(R.string.public_string_cancel), new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        }, getString(R.string.public_string_confirm), new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoRegister(name, pwd);
            }
        });
    }

    class ViewHolder
    {
        EditText viewUserName;
        EditText viewUserPwd;
        TextView viewBtnLogin;
    }
}
