package com.mobile.love.enjoy.consume.custom.dialog;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.love.enjoy.consume.R;

/**
 * Created by JerryCaicos on 2017/1/19.
 */

public class LoadingDialog extends BaseDialogFragment
{
    private static final String TAG = "LoadingDialog";

    protected static final String KEY_MESSAGE = "message";

    private DialogInterface.OnCancelListener onCancelListener;

    private DialogInterface.OnDismissListener onDismissListener;

    private DialogInterface.OnShowListener onShowListener;

    private OnBackKeyPressedListener onBackKeyPressedListener;

    public void setOnCancelListener(DialogInterface.OnCancelListener cancelListener)
    {
        onCancelListener = cancelListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener)
    {
        onDismissListener = dismissListener;
    }

    public void setOnShowListener(DialogInterface.OnShowListener showListener)
    {
        onShowListener = showListener;
    }

    public void setOnBackKeyPressedListener(OnBackKeyPressedListener backKeyPressedListener)
    {
        onBackKeyPressedListener = backKeyPressedListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_base_loading_dialog, container, false);

        TextView textView = (TextView) view.findViewById(R.id.view_base_loading_view_hint);

        Bundle bundle = getArguments();

        String message = bundle == null ? "" : bundle.getString(KEY_MESSAGE);

        if(TextUtils.isEmpty(message))
        {
            textView.setVisibility(View.GONE);
        }
        else
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
        }

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getDialog().setOnShowListener(onShowListener);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent)
            {
                if(onBackKeyPressedListener != null)
                {
                    onBackKeyPressedListener.onBackPressed();
                }
                return true;
            }
        });
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
    public String getFragmentTitle()
    {
        return TAG;
    }

    public interface OnBackKeyPressedListener
    {
        void onBackPressed();
    }

    public static class Controller
    {
        private Bundle mBundle;

        private DialogInterface.OnShowListener onShowListener;

        private DialogInterface.OnDismissListener onDismissListener;

        private DialogInterface.OnCancelListener onCancelListener;

        private OnBackKeyPressedListener onBackKeyPressedListener;

        private LoadingDialog loadingDialog;

        public Controller()
        {
            mBundle = new Bundle();
        }

        public Controller setMessage(String message)
        {
            mBundle.putCharSequence(KEY_MESSAGE, message);
            return this;
        }

        public Controller setOnBackPressedListener(OnBackKeyPressedListener backPressedListener)
        {
            onBackKeyPressedListener = backPressedListener;
            return this;
        }

        public Controller setOnShowListener(DialogInterface.OnShowListener showListener)
        {
            onShowListener = showListener;
            return this;
        }

        public Controller setOnDismissListener(DialogInterface.OnDismissListener dismissListener)
        {
            onDismissListener = dismissListener;
            return this;
        }

        private LoadingDialog create()
        {
            LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.setOnShowListener(onShowListener);
            loadingDialog.setOnDismissListener(onDismissListener);
            loadingDialog.setOnCancelListener(onCancelListener);
            loadingDialog.setOnBackKeyPressedListener(onBackKeyPressedListener);
            loadingDialog.setArguments(mBundle);

            return loadingDialog;
        }

        public LoadingDialog getLoadingDialog()
        {
            if(loadingDialog == null)
            {
                loadingDialog = create();
            }
            return loadingDialog;
        }

        public void show(FragmentManager manager)
        {
            if(manager == null)
            {
                Log.e(TAG, "show error : fragment manager is null.");
                return;
            }
            if(loadingDialog == null)
            {
                loadingDialog = create();
            }
            FragmentManager fm = loadingDialog.getFragmentManager();
            if(fm != null)
            {
                Log.e(TAG, "show error : fragment manager has already added. show it.");
                fm.beginTransaction().show(loadingDialog).commit();
                return;
            }
            Log.d(TAG, "show loading dialog");
            loadingDialog.show(manager, loadingDialog.getTag());

        }

        public void dismiss()
        {
            if(loadingDialog == null)
            {
                Log.e(TAG, "dismiss error : loading dialog is null.");
                return;
            }
            if(loadingDialog.getFragmentManager() == null)
            {
                Log.e(TAG, "dismiss error : loaging dialog fragment manager is null.");
                return;
            }
            Log.d(TAG, "dismiss loading dialog");
            loadingDialog.dismissAllowingStateLoss();
        }
    }
}
