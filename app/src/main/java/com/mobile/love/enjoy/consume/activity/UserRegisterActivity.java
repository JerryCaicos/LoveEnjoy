package com.mobile.love.enjoy.consume.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.BaseActivity;
import com.mobile.love.enjoy.consume.R;
import com.mobile.love.enjoy.consume.service.DBHelper;
import com.mobile.love.enjoy.consume.service.TableConstants;
import com.mobile.love.enjoy.consume.service.databean.UserBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenaxing on 2017/2/22.
 */

public class UserRegisterActivity extends BaseActivity
{
    public static final String KEY_REGISTER_ID = "register_id";
    public static final String KEY_REGISTER_NAME = "register_name";
    public static final String KEY_REGISTER_PWD = "register_pwd";

    private ViewHolder viewHolder;

    private String userId;

    private String userPwd;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register, true);
        setHeaderTitle(R.string.string_title_action_text_register);
        viewHolder = new ViewHolder();
        initParams();
        initActivity();
    }

    private void initActivity()
    {
        viewHolder.userAccountId = (EditText) findViewById(R.id.view_register_user_id);
        viewHolder.userAccountName = (EditText) findViewById(R.id.view_register_user_name);
        viewHolder.userAccountPwd = (EditText) findViewById(R.id.view_register_user_pwd);
        viewHolder.userAccountPwdConfirm = (EditText) findViewById(R.id.view_register_user_pwd_confirm);
        viewHolder.userSexMan = (CheckBox) findViewById(R.id.view_register_user_sex_man);
        viewHolder.userSexWoman = (CheckBox) findViewById(R.id.view_register_user_sex_woman);
        viewHolder.userAccountDesc = (EditText) findViewById(R.id.view_register_user_desc);
        viewHolder.userRegisterBtn = (TextView) findViewById(R.id.view_register_user_register_btn);

        viewHolder.userSexWoman.setOnClickListener(onClickListener);
        viewHolder.userSexMan.setOnClickListener(onClickListener);
        viewHolder.userRegisterBtn.setOnClickListener(onClickListener);

        viewHolder.userAccountId.setText(userId);
        viewHolder.userAccountPwd.setText(userPwd);
        viewHolder.userAccountPwdConfirm.setText(userPwd);
    }


    private void initParams()
    {
        Intent intent = getIntent();
        userId = intent.getStringExtra(KEY_REGISTER_ID);
        userPwd = intent.getStringExtra(KEY_REGISTER_PWD);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch(view.getId())
            {
                case R.id.view_register_user_sex_man:
                    setSexManChecked(viewHolder.userSexMan.isChecked());
                    break;
                case R.id.view_register_user_sex_woman:
                    setSexManChecked(!viewHolder.userSexWoman.isChecked());
                    break;
                case R.id.view_register_user_register_btn:
                    doRegister();
                    break;
                default:
                    break;
            }
        }
    };

    private void doRegister()
    {
        final String accountId = viewHolder.userAccountId.getText().toString();
        final String accountName = viewHolder.userAccountName.getText().toString();
        final String accountPwd = viewHolder.userAccountPwd.getText().toString();
        String accountConfirmPwd = viewHolder.userAccountPwdConfirm.getText().toString();
        final String accountDesc = viewHolder.userAccountDesc.getText().toString();
        final boolean manChecked = viewHolder.userSexMan.isChecked();
        boolean womanChecked = viewHolder.userSexWoman.isChecked();

        boolean ignoreDesc = false;

        if(TextUtils.isEmpty(accountId))
        {
            showToastMsg(R.string.string_register_input_id);
            return;
        }
        if(TextUtils.isEmpty(accountName))
        {
            showToastMsg(R.string.string_register_input_name);
            return;
        }
        if(TextUtils.isEmpty(accountPwd))
        {
            showToastMsg(R.string.string_register_input_pwd);
            return;
        }
        if(TextUtils.isEmpty(accountConfirmPwd))
        {
            showToastMsg(R.string.string_register_input_confirm_pwd);
            return;
        }
        if(!accountPwd.equals(accountConfirmPwd))
        {
            showToastMsg(R.string.string_register_input_confirm_pwd_error);
            return;
        }
        if(TextUtils.isEmpty(accountDesc))
        {
            showDialog(null, getString(R.string.string_register_input_desc), getString(R.string.public_string_cancel), new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    viewHolder.userAccountDesc.requestFocus();
                    viewHolder.userAccountDesc.performClick();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, getString(R.string.public_string_confirm), new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    registerUser(manChecked, accountId, accountName, accountPwd, accountDesc);
                }
            });
            return;
        }
        registerUser(manChecked, accountId, accountName, accountPwd, accountDesc);
    }

    private void registerUser(boolean isMan, String id, String name, String pwd, String desc)
    {
        showLoadingView(false);
        UserBean user = new UserBean();
        user.userId = id;
        user.userName = name;
        user.userPwd = pwd;
        user.userSex = isMan ? 1 : 0;
        user.userDesc = desc;
        boolean isExist = isAccountExist(user);
        if(isExist)
        {
            showToastMsg(R.string.string_register_user_already_exist);
        }
        else
        {
            DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
            dbHelper.insertBean(UserBean.class, user);
            boolean isSuccess = isAccountExist(user);
            if(isSuccess)
            {
                showToastMsg(R.string.string_register_user_register_success);
            }
            else
            {
                showToastMsg(R.string.string_register_user_register_failure);
            }
        }
    }

    private boolean isAccountExist(UserBean userBean)
    {
        boolean isExist = false;
        Map<String, String> map = new HashMap<>();
        map.put(TableConstants.USERID, userBean.userId);
        map.put(TableConstants.USERNAME, userBean.userName);
        map.put(TableConstants.USERPWD, userBean.userPwd);
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
        List<UserBean> list = dbHelper.queryObjectBean(UserBean.class, map);

        if(list != null && list.size()>0)
        {
            isExist = true;
        }
        return isExist;
    }

    private void setSexManChecked(boolean checked)
    {
        if(checked)
        {
            viewHolder.userSexMan.setChecked(true);
            viewHolder.userSexMan.setTextColor(getResources().getColor(R.color.white));
            viewHolder.userSexWoman.setChecked(false);
            viewHolder.userSexWoman.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else
        {
            viewHolder.userSexWoman.setChecked(true);
            viewHolder.userSexWoman.setTextColor(getResources().getColor(R.color.white));
            viewHolder.userSexMan.setChecked(false);
            viewHolder.userSexMan.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    class ViewHolder
    {
        EditText userAccountId;
        EditText userAccountName;
        EditText userAccountPwd;
        EditText userAccountPwdConfirm;
        CheckBox userSexMan;
        CheckBox userSexWoman;
        EditText userAccountDesc;
        TextView userRegisterBtn;
    }
}
