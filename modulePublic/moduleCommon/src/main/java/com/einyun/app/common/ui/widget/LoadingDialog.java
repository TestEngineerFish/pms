package com.einyun.app.common.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.einyun.app.common.R;

public class LoadingDialog extends Dialog {
        public LoadingDialog(Context context) {
            super(context);
        }

        public LoadingDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        public static class Builder {
            private Context context;
            private String message;
            private boolean isCancelable;
            private boolean isCancelOutside;
            private ProgressBar progress_bar;

            public Builder(Context context) {
                this.context = context;
            }

            public LoadingDialog.Builder setMessage(String message) {
                this.message = message;
                return this;
            }

            public LoadingDialog.Builder setCancelable(boolean isCancelable) {
                this.isCancelable = isCancelable;
                return this;
            }

            public LoadingDialog.Builder setCancelOutside(boolean isCancelOutside) {
                this.isCancelOutside = isCancelOutside;
                return this;
            }

            public LoadingDialog create() {
                LayoutInflater inflater = LayoutInflater.from(this.context);
                View view = inflater.inflate(R.layout.dialog_loading_two, (ViewGroup)null);
                this.progress_bar = (ProgressBar)view.findViewById(R.id.progress_bar);
                LoadingDialog loadingDailog = new LoadingDialog(this.context, R.style.MyDialogLoadingStyle);
                loadingDailog.setCancelable(false);
                loadingDailog.setCanceledOnTouchOutside(false);
                loadingDailog.setContentView(view);
                return loadingDailog;
            }
        }
}
