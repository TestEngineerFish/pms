package com.einyun.app.base.http;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//import com.dhh.easy.business.common.ui.widget.LoadingDialog;

/**
 * Description: RxJava调度管理
 *
 * @Author: yaozhjheng
 * @Date: 2018/11/6
 */
public class RxSchedulers {

    /**
     * RxJava io和main线程
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> inIoMain() {

        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * RxJava io和main线程
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> inIo() {

        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

//    /**
//     * RxJava io和main线程,是否显示加载框
//     *
//     * @param context 上下文环境
//     * @param isShow  是否显示loading
//     * @param <T>
//     * @return
//     */
//    public static <T> FlowableTransformer<T, T> inIoMainLoading(Context context, final boolean isShow) {
//        LoadingDialog loadingDialog=null;
//        if(context!=null&&context instanceof Activity){
//            loadingDialog = new LoadingDialog.Builder(context).create();
//        }
//        LoadingDialog finalSubScribeDialog = loadingDialog;
//        LoadingDialog finalLoadingDialog = loadingDialog;
//        return upstream -> upstream.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(subscription -> {
//                    if (isShow&& finalSubScribeDialog !=null) {
//                        finalSubScribeDialog.show();
//                    }
//                })
//                .doOnTerminate(() -> {
//                    if (isShow&& finalLoadingDialog !=null&& finalLoadingDialog.isShowing()) {
//                        finalLoadingDialog.dismiss();
//                    }
//                });
//    }


//    /**
//     * RxJava io和main线程,并显示加载框
//     *
//     * @param context 上下文环境
//     * @param <T>
//     * @return
//     */
//    public static <T> FlowableTransformer<T, T> inIoMainLoading(Context context) {
//        return inIoMainLoading(context, true);
//    }


}
