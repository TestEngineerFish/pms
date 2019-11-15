package com.einyun.app.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.einyun.app.base.R;


/**
 * Created by ShM's on 2018/4/9.
 */

public class LoadingDialog extends Dialog {


    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{

        private Context context;
        private String message;
        private boolean isCancelable;
        private boolean isCancelOutside;
        private ProgressBar progress_bar;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         * @param message
         * @return
         */

        public Builder setMessage(String message){
            this.message=message;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside){
            this.isCancelOutside=isCancelOutside;
            return this;
        }

        public LoadingDialog create(){
            LayoutInflater inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.dialog_loading,null);
            progress_bar =(ProgressBar) view.findViewById(R.id.progress_bar);
            LoadingDialog loadingDailog=new LoadingDialog(context, R.style.MyDialogLoadingStyle);
            loadingDailog.setCancelable(false);
            loadingDailog.setCanceledOnTouchOutside(false);
            loadingDailog.setContentView(view);
            return  loadingDailog;

        }


    }
}