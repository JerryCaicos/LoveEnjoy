package com.mobile.love.enjoy.consume.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.BaseActivity;
import com.mobile.love.enjoy.consume.R;
import com.mobile.love.enjoy.consume.custom.imageview.CircleImageView;
import com.mobile.love.enjoy.consume.service.DBHelper;
import com.mobile.love.enjoy.consume.service.databean.UserBean;
import com.mobile.love.enjoy.consume.utils.LogUtils;
import com.mobile.love.enjoy.consume.utils.ToastUtils;

import java.util.List;

/**
 * Created by chenaxing on 2017/1/16.
 */

public class HomeActivity extends BaseActivity
{
    private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewHolder = new ViewHolder();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main, false);

        initCompent();
    }

    private void initCompent()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        viewHolder.appBarLayout = (AppBarLayout) findViewById(R.id.layout_appbar);
        viewHolder.collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.layout_collapsing_toolbar);
        viewHolder.viewBigBg = (ImageView) findViewById(R.id.view_home_big_bg);
        viewHolder.layoutUserInfo = (FrameLayout) findViewById(R.id.layout_user_info);
        viewHolder.viewUserName = (TextView) findViewById(R.id.view_user_name);
        viewHolder.viewUserInfo = (TextView) findViewById(R.id.view_user_info);
        viewHolder.layoutNestedScrollView = (NestedScrollView) findViewById(R.id.layout_nested_scroll_view);
        viewHolder.layoutContainerAll = (LinearLayout) findViewById(R.id.layout_content_container);
        viewHolder.layoutContainerOne = (LinearLayout) findViewById(R.id.layout_container_hor_one);
        viewHolder.layoutContainerTwo = (LinearLayout) findViewById(R.id.layout_container_hor_two);
        viewHolder.toolbarBottom = (Toolbar) findViewById(R.id.layout_toolbar_bottom);
        viewHolder.viewUserTitleName = (TextView) findViewById(R.id.view_user_title_name);
        viewHolder.viewUserHeader = (CircleImageView) findViewById(R.id.view_user_header);
        viewHolder.layoutConsumeItemInput = (LinearLayout) findViewById(R.id.layout_consume_item_input);
        viewHolder.layoutConsumeCategoryEdit = (LinearLayout) findViewById(R.id.layout_consume_category_edit);
        viewHolder.layoutConsumeItemEdit = (LinearLayout) findViewById(R.id.layout_consume_item_edit);
        viewHolder.layoutConsumeLogin = (LinearLayout) findViewById(R.id.layout_consume_login);
        viewHolder.layoutConsumeHappyItem = (LinearLayout) findViewById(R.id.layout_consume_happy);
        viewHolder.layoutConsumeMoreItem = (LinearLayout) findViewById(R.id.layout_consume_more);

        CollapsingToolbarLayout.LayoutParams bigBgParams = (CollapsingToolbarLayout.LayoutParams) viewHolder.viewBigBg.getLayoutParams();
        CollapsingToolbarLayout.LayoutParams userInfoParams = (CollapsingToolbarLayout.LayoutParams) viewHolder.layoutUserInfo.getLayoutParams();

        bigBgParams.setParallaxMultiplier(0.7f);
        userInfoParams.setParallaxMultiplier(0.3f);

        initOnclickListener();
    }

    private void initOnclickListener()
    {
        viewHolder.viewBigBg.setOnClickListener(onClickListener);
        viewHolder.viewUserHeader.setOnClickListener(onClickListener);
        viewHolder.layoutConsumeItemInput.setOnClickListener(onClickListener);
        viewHolder.layoutConsumeCategoryEdit.setOnClickListener(onClickListener);
        viewHolder.layoutConsumeItemEdit.setOnClickListener(onClickListener);
        viewHolder.layoutConsumeLogin.setOnClickListener(onClickListener);
        viewHolder.layoutConsumeHappyItem.setOnClickListener(onClickListener);
        viewHolder.layoutConsumeMoreItem.setOnClickListener(onClickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addUser()
    {
        UserBean userBean = new UserBean();
        userBean.userId = "1233456";
        userBean.userName = "zhangsan";
        userBean.userSex = 1;
        userBean.userDesc = "shabi";
        userBean.userLevel = 1;
        userBean.userLevelStar = 1;
        DBHelper.getInstance(this).insertBean(UserBean.class, userBean);
        query();
    }

    private void query()
    {
        List<UserBean> list = DBHelper.getInstance(this).queryBeans(UserBean.class);
        LogUtils.d("" + list.size());
        ToastUtils.showMessage(list.get(0).userName);
        ToastUtils.showMessage(list.get(0).userName);
        ToastUtils.showMessage(list.get(0).userName);
    }

    @Override
    protected boolean onBackKeyPressed()
    {
        LogUtils.d("MainActivity onBackKeyPressed");
        return super.onBackKeyPressed();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch(view.getId())
            {

                case R.id.view_home_big_bg:
                    /**最上面大背景点击**/

                    break;
                case R.id.view_user_header:
                    /**用户头像点击**/

                    break;
                case R.id.layout_consume_item_input:
                    /**消费记录录入按钮**/

                    break;
                case R.id.layout_consume_category_edit:
                    /**消费类型编辑按钮**/
                    editCategory();
                    break;
                case R.id.layout_consume_item_edit:
                    /**消费记录查看/编辑**/

                    break;
                case R.id.layout_consume_login:
                    /**用户登录/注册**/
                    gotoLogin();
                    break;
                case R.id.layout_consume_happy:
                    /**娱乐按钮点击**/
                    showToastMsg(R.string.string_function_in_production);
                    break;
                case R.id.layout_consume_more:
                    /**更多按钮点击**/
                    showToastMsg(R.string.string_function_in_production);
                    showDialog();
                    break;
                default:
                    break;
            }
        }
    };

    private void showDialog()
    {
        showDialog("sfsfsfsfs", "sdfdsfsfsf", "sfsdfs", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        }, "sfsdfds", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

    private void editCategory()
    {
        Intent intent = new Intent();
        intent.setClass(this, EditCategoryActivity.class);
        startActivity(intent);
    }

    private void gotoLogin()
    {
        Intent intent = new Intent();
        intent.setClass(this, UserLoginActivity.class);
        startActivity(intent);
    }


    @Override
    public Resources getResources()
    {
        return super.getResources();
    }

    class ViewHolder
    {
        AppBarLayout appBarLayout;

        CollapsingToolbarLayout collapsingToolbarLayout;

        ImageView viewBigBg;

        FrameLayout layoutUserInfo;

        TextView viewUserName;

        TextView viewUserInfo;

        NestedScrollView layoutNestedScrollView;

        LinearLayout layoutContainerAll;

        LinearLayout layoutContainerOne;

        LinearLayout layoutContainerTwo;

        Toolbar toolbarBottom;

        TextView viewUserTitleName;

        CircleImageView viewUserHeader;

        LinearLayout layoutConsumeItemInput;

        LinearLayout layoutConsumeCategoryEdit;

        LinearLayout layoutConsumeItemEdit;

        LinearLayout layoutConsumeLogin;

        LinearLayout layoutConsumeHappyItem;

        LinearLayout layoutConsumeMoreItem;
    }
}
