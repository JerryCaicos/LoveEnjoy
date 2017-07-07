package com.mobile.love.enjoy.consume;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.mobile.love.enjoy.consume.custom.dialog.BaseDialogFragment;
import com.mobile.love.enjoy.consume.custom.dialog.CustomDialog;
import com.mobile.love.enjoy.consume.custom.dialog.LoadingDialog;
import com.mobile.love.enjoy.consume.custom.header.HeaderBuilder;
import com.mobile.love.enjoy.consume.service.DBHelper;
import com.mobile.love.enjoy.consume.service.databean.CategoryBean;
import com.mobile.love.enjoy.consume.utils.BaseUtils;
import com.mobile.love.enjoy.consume.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenaxing on 2017/1/17.
 */

public class BaseActivity extends AppCompatActivity
{
    protected final String TAG = this.getClass().getSimpleName();

    private LoadingDialog.Controller dialogController;

    private List<BaseDialogFragment> dialogList;

    private HeaderBuilder mHeaderBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish()
    {
        super.finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        dialogList = new ArrayList<>();
    }

    public void setContentView(int layoutResId, boolean showTitle)
    {
        if(showTitle)
        {
            View view = getLayoutInflater().inflate(layoutResId, null);
            setContentView(getContentViewWithTitle(view));
        }
        else
        {
            setContentView(layoutResId);
        }
    }

    public View getContentViewWithTitle(View view)
    {
        LinearLayout activityContainer = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.view_base_activity_content_container, null);
        mHeaderBuilder = new HeaderBuilder(this);
        onCreateHeader(mHeaderBuilder);
        View headerView = mHeaderBuilder.getHeaderView();
        activityContainer.addView(headerView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.public_dimen_fourty_eight)));
        activityContainer.addView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        return activityContainer;
    }

    public void onCreateHeader(HeaderBuilder headerBuilder)
    {
        headerBuilder.setBackActionListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(onBackKeyPressed())
                {
                    return;
                }
                finish();
            }
        });
    }

    public void setHeaderTitle(CharSequence title)
    {
        mHeaderBuilder.setHeaderTitle(title);
    }

    public void setHeaderTitle(int resId)
    {
        mHeaderBuilder.setHeaderTitle(resId);
    }

    public void setHeaderTitle(View view)
    {
        mHeaderBuilder.setHeaderTitle(view);
    }

    public void addTextAction(CharSequence name, View.OnClickListener listener)
    {
        mHeaderBuilder.setTextAction(name, listener);
    }

    public void addTextAction(int resId, View.OnClickListener listener)
    {
        mHeaderBuilder.setTextAction(resId, listener);
    }

    public void addIconAction(int resId, View.OnClickListener listener)
    {
        mHeaderBuilder.setIconAction(resId, listener);
    }

    public void addIconAction(Drawable icon, View.OnClickListener listener)
    {
        mHeaderBuilder.setIconAction(icon, listener);
    }

    public void setBackBtnVisibility(boolean show)
    {
        mHeaderBuilder.setBackActionVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setHeaderTitleVisibility(boolean show)
    {
        mHeaderBuilder.setHeaderTitleVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setHeaderBackBtnListener(View.OnClickListener listener)
    {
        mHeaderBuilder.setBackActionListener(listener);
    }

    public void setHeaderActionVisibility(boolean show)
    {
        mHeaderBuilder.setHeaderActionVisibility(show ? View.VISIBLE : View.GONE);
    }


    public void showToastMsg(int resId)
    {
        ToastUtils.showMessage(resId);
    }

    public void showToastMsg(String msg)
    {
        ToastUtils.showMessage(msg);
    }

    public void showLoadingView(boolean enable)
    {
        if(isFinishing())
        {
            return;
        }
        if(dialogController == null)
        {
            dialogController = new LoadingDialog.Controller();
            dialogController.setOnBackPressedListener(new LoadingDialog.OnBackKeyPressedListener()
            {
                @Override
                public void onBackPressed()
                {
                    if(!onBackKeyPressed())
                    {
                        finish();
                    }
                }
            });
        }
        dialogController.getLoadingDialog().setCancelable(enable);
        if(dialogList == null || !isFinishing())
        {
            dialogController.show(getFragmentManager());
        }
    }

    public void hideLoadingView()
    {
        if(dialogController != null)
        {
            dialogController.dismiss();
        }
    }

    public void showDialog(String title, String message, String leftBtnName,
            View.OnClickListener leftClickListener, String rightBtnName,
            View.OnClickListener rightClickListener)
    {
        BaseDialogFragment dialog = new CustomDialog.Builder()
                .setTilter(title)
                .setMessage(message)
                .setLeftBtn(leftBtnName, leftClickListener)
                .setRightBtn(rightBtnName, rightClickListener)
                .setCancelable(false)
                .create();
        displayDialog(dialog);
    }

    private void displayDialog(BaseDialogFragment dialog)
    {
        if(isFinishing())
        {
            return;
        }
        if(dialogList != null)
        {
            dialogList.add(dialog);
        }
        else
        {
            dialog.show(getFragmentManager(), dialog.getFragmentTitle());
        }
    }

    public void initCategoryData()
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
                    dbHelper.insertBean(CategoryBean.class, list.get(i));
                }
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(dialogList != null && dialogList.size() > 0)
        {
            for(BaseDialogFragment dialog : dialogList)
            {
                dialog.show(getFragmentManager(), dialog.getTag());
            }
            dialogList.clear();
            dialogList = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // 物理返回键按下的时候,也调用一下onBackKeyPressed.
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(onBackKeyPressed())
            {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 当统一标题左边的返回按钮被点击,或者物理返回键被点击,都会调用这个方法.<BR>
     *
     * @return true, 表示已经处理,上层不会再继续执行; false, 上层将继续执行.
     */
    protected boolean onBackKeyPressed()
    {
        return false;
    }
}
