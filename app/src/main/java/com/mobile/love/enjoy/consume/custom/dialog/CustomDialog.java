package com.mobile.love.enjoy.consume.custom.dialog;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.R;

/**
 * Created by chenaxing on 2017/1/20.
 */

public class CustomDialog extends BaseDialogFragment
{

    private static final String TAG = "CustomDialog";

    private static final String KEY_TITLE = "KeyOfTitle";

    private static final String KEY_MESSAGE = "KeyOfMessage";

    private static final String KEY_LEFT_NAME = "KeyOFLeftBtnName";

    private static final String KEY_RIGHT_NAME = "KeyOfRightBtnName";

    private static final String KEY_CANCEL_ENABEL = "KeyOfCancelable";

    private TextView leftBtn;

    private TextView rightBtn;

    private DialogInterface.OnDismissListener onDismissListener;

    private DialogInterface.OnShowListener onShowListener;

    private DialogInterface.OnCancelListener onCancelListener;

    private View.OnClickListener leftOnClickListener;

    private View.OnClickListener rightOnClickListener;

    public CustomDialog()
    {

    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener)
    {
        onDismissListener = dismissListener;
    }

    public void setOnShowListener(DialogInterface.OnShowListener showListener)
    {
        onShowListener = showListener;
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener cancelListener)
    {
        onCancelListener = cancelListener;
    }

    public void setLeftOnClickListener(View.OnClickListener clickListener)
    {
        leftOnClickListener = clickListener;
    }

    public void setRightOnClickListener(View.OnClickListener clickListener)
    {
        rightOnClickListener = clickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.customdialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_base_custom_dialog, container, false);

        Bundle bundle = getArguments();

        TextView titleView = (TextView) view.findViewById(R.id.view_custom_dialog_title);
        String title = bundle.getString(KEY_TITLE);

        if(TextUtils.isEmpty(title))
        {
            titleView.setVisibility(View.GONE);
            view.findViewById(R.id.view_custom_dialog_title_divider).setVisibility(View.GONE);
        }
        else
        {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.view_custom_dialog_title_divider).setVisibility(View.VISIBLE);
        }

        String message = bundle.getString(KEY_MESSAGE);
        TextView contentView = (TextView) view.findViewById(R.id.view_custom_dialog_content);
        if(TextUtils.isEmpty(message))
        {
            contentView.setVisibility(View.GONE);
        }
        else
        {
            contentView.setText(message);
            contentView.setVisibility(View.VISIBLE);
        }

        String leftName = bundle.getString(KEY_LEFT_NAME);
        leftBtn = (TextView) view.findViewById(R.id.view_custom_dialog_left_btn);
        if(!TextUtils.isEmpty(leftName))
        {
            leftBtn.setText(leftName);
            leftBtn.setVisibility(View.VISIBLE);
            leftBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(leftOnClickListener != null)
                    {
                        leftOnClickListener.onClick(v);
                    }
                    dismissAllowingStateLoss();
                }
            });
        }
        else
        {
            if(leftOnClickListener == null)
            {
                leftBtn.setVisibility(View.GONE);
            }
            else
            {
                leftBtn.setVisibility(View.VISIBLE);
                leftBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(leftOnClickListener != null)
                        {
                            leftOnClickListener.onClick(v);
                        }
                        dismissAllowingStateLoss();
                    }
                });
            }
        }

        String rightName = bundle.getString(KEY_RIGHT_NAME);
        rightBtn = (TextView) view.findViewById(R.id.view_custom_dialog_right_btn);
        if(!TextUtils.isEmpty(rightName))
        {
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setText(rightName);
            rightBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(rightOnClickListener != null)
                    {
                        rightOnClickListener.onClick(v);
                    }
                    dismissAllowingStateLoss();
                }
            });
        }
        else
        {
            if(rightOnClickListener == null)
            {
                rightBtn.setVisibility(View.GONE);
            }
            else
            {
                rightBtn.setVisibility(View.VISIBLE);
                rightBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(rightOnClickListener != null)
                        {
                            rightOnClickListener.onClick(v);
                        }
                        dismissAllowingStateLoss();
                    }
                });
            }
        }


        setCancelable(bundle.getBoolean(KEY_CANCEL_ENABEL, true));

        return view;
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
        if(onCancelListener != null)
        {
            onCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
        if(onDismissListener != null)
        {
            onDismissListener.onDismiss(dialog);
        }
    }


    @Override
    public void onStart()
    {
        Window window = getDialog().getWindow();
        WindowManager windowManager = window.getWindowManager();
        Point windowSize = new Point();
        windowManager.getDefaultDisplay().getSize(windowSize);
        window.getAttributes().width = (int) (windowSize.x * 0.80f);
        window.getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);

        super.onStart();

        View btnDivider = getView().findViewById(R.id.view_custom_dialog_btn_divider);
        if(leftBtn != null && rightBtn != null && btnDivider != null)
        {
            if(leftBtn.getVisibility() == View.VISIBLE && rightBtn.getVisibility() == View.VISIBLE)
            {
                btnDivider.setVisibility(View.VISIBLE);
            }
            else
            {
                rightBtn.setBackground(getResources().getDrawable(R.drawable.drawable_dialog_bottom_btn_bg_2));
                btnDivider.setVisibility(View.GONE);
            }
        }
        getDialog().setOnShowListener(onShowListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public String getFragmentTitle()
    {
        return TAG;
    }

    public static class Builder
    {
        private Bundle bundle;

        private View.OnClickListener leftClickListener;

        private View.OnClickListener rightClickListener;

        private DialogInterface.OnShowListener onShowListener;

        private DialogInterface.OnDismissListener onDismissListener;

        private DialogInterface.OnCancelListener onCancelListener;

        public Builder()
        {
            bundle = new Bundle();
        }

        public Builder setTilter(CharSequence sequence)
        {
            bundle.putCharSequence(KEY_TITLE, sequence);
            return this;
        }

        public Builder setMessage(CharSequence sequence)
        {
            bundle.putCharSequence(KEY_MESSAGE, sequence);
            return this;
        }

        public Builder setLeftBtn(CharSequence sequence, View.OnClickListener clickListener)
        {
            leftClickListener = clickListener;
            bundle.putCharSequence(KEY_LEFT_NAME, sequence);
            return this;
        }

        public Builder setRightBtn(CharSequence sequence, View.OnClickListener clickListener)
        {
            rightClickListener = clickListener;
            bundle.putCharSequence(KEY_RIGHT_NAME, sequence);
            return this;
        }

        public Builder setCancelable(boolean enable)
        {
            bundle.putBoolean(KEY_CANCEL_ENABEL, enable);
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener dismissListener)
        {
            onDismissListener = dismissListener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener cancelListener)
        {
            onCancelListener = cancelListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener showListener)
        {
            onShowListener = showListener;
            return this;
        }

        public CustomDialog create()
        {
            final CustomDialog dialog = new CustomDialog();
            dialog.setOnShowListener(onShowListener);
            dialog.setOnCancelListener(onCancelListener);
            dialog.setOnDismissListener(onDismissListener);
            dialog.setLeftOnClickListener(leftClickListener);
            dialog.setRightOnClickListener(rightClickListener);
            dialog.setArguments(bundle);

            return dialog;
        }

        public void show(FragmentManager manager)
        {
            if(manager == null)
            {
                Log.e(TAG, "custom dialog show error : fragment manager is null.");
                return;
            }

            CustomDialog dialog = create();
            Log.d(TAG, "custom dialog show.");
            dialog.show(manager, dialog.getFragmentTitle());

        }


    }
}
