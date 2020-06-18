package allpointech.agency.network.http.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.Html;

import allpointech.agency.R;
import allpointech.agency.network.http.resource.ResourceError;

public class HttpPopupManager {

    private static PopupDialog mDialog;

    public static PopupDialog getPopup(Context context, int errorcode, String message, String title, final PopupDialog.OnResultListener listener) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        if (message == null) message = "";
        mDialog = new PopupDialog(context);
        mDialog.setTitle(title);
        mDialog.setErrorCode(errorcode);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(Html.fromHtml(message));
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.comm_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDialogResult(mDialog, "close");
            }
        });
        return mDialog;
    }


    public static PopupDialog getPopup(Context context, int errorcode, String message, final PopupDialog.OnResultListener listener) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        if (message == null) message = "";
        mDialog = new PopupDialog(context);
        mDialog.setTitle(context.getString(R.string.comm_alert));
        mDialog.setErrorCode(errorcode);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(Html.fromHtml(message));
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.comm_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDialogResult(mDialog, "close");
            }
        });
        return mDialog;
    }

    public static PopupDialog getPopup(Context context, int errorCode, final PopupDialog.OnResultListener listener) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new PopupDialog(context);
        mDialog.setTitle(context.getString(R.string.comm_alert));
        mDialog.setErrorCode(errorCode);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(ResourceError.getErrorMsg(errorCode));
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.comm_ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDialogResult(mDialog, "close");
            }
        });
        return mDialog;
    }

    public static class PopupDialog extends AlertDialog {
        public static int nErrorCode;

        public static interface OnResultListener {
            public void onDialogResult(Dialog dialog, String... results);
        }

        public OnResultListener mListener;

        public void setOnResultListener(OnResultListener listener) {
            mListener = listener;
        }

        public PopupDialog(Context context) {
            super(context);
        }

        public void setErrorCode(int errorCode) {
            nErrorCode = errorCode;
        }
    }
}

