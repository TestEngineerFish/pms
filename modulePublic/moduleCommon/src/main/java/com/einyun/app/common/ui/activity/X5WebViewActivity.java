package com.einyun.app.common.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BaseViewModelActivity;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.databinding.ActivityX5WebViewBinding;
import com.einyun.app.common.model.SerializableMap;
import com.einyun.app.common.service.RouterUtils;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.activity
 * @ClassName: X5WebViewActivity
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/14 0014 下午 15:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/14 0014 下午 15:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_X5_WEBVIEW)
public class X5WebViewActivity extends BaseHeadViewModelActivity<ActivityX5WebViewBinding, BaseViewModel> {
    @Autowired(name = RouteKey.KEY_WEB_URL)
    String webUrl;

    @Autowired(name=RouteKey.KEY_WEB_TITLE)
    String webTitle;

    @Autowired(name = RouteKey.KEY_PARAMS)
    Bundle bundle;

    SerializableMap params;

    private WebSettings webSettings;// WebSettings对象

    @Override
    protected BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_x5_web_view;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        if (webTitle != null){
            setHeadTitle(webTitle);
        }
        binding.headBar.mainHeaderBar.setBackground(getResources().getDrawable(R.color.main_color));

        // 清除网页访问留下的缓存
        // 由于内核缓存是全局的因此这个方法不仅仅针对webView而是针对整个应用程序
        binding.webview.clearCache(true);

        // 清除当前webView的访问历史记录
        binding.webview.clearHistory();

        // 这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        binding.webview.clearFormData();
        // 获取WebSettings对象
        webSettings = binding.webview.getSettings();
        // 特别注意：5.1以上默认禁止了https和http混用。下面代码是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 21

        }
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存，直接用网络加载
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 告诉js可以自动打开window

        // 两者一起使用，可以让html页面加载显示适应手机的屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        // 即允许在 File 域下执行任意 JavaScript 代码
//        webSettings.setAllowFileAccess(true);// 设置是否允许 WebView 使用 File 协议
        // 禁止 file 协议加载 JavaScript
//        if (url.startsWith("file://")){
//            webSettings.setJavaScriptEnabled(false);
//        }else {
//            webSettings.setJavaScriptEnabled(true);
//        }

        webSettings.setSavePassword(false);// 关闭密码保存提醒；该方法在以后的版本中该方法将不被支持

        webSettings.setDomStorageEnabled(true);// 设置支持DOM storage API
        // 通过addJavascriptInterface()将Java对象映射到JS对象
//        binding.webview.addJavascriptInterface(new AndroidToJs(getContext()), "androidObj");

    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.main_color);
    }

    @Override
    protected void initData() {
        super.initData();
        if (bundle != null) {
            params = (SerializableMap) bundle.get(RouteKey.KEY_MAP_SERIALIZABLE);
        }
        if (params != null) {
            binding.webview.loadUrl(webUrl, params.getMap());
        } else {
            binding.webview.loadUrl(webUrl);
        }
        Logger.d("X5WebView load->" + webUrl);
        binding.webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int progress) {
                super.onProgressChanged(webView, progress);
                binding.progressBar.setProgress(progress);
                if (progress != 100) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });
        binding.webview.setWebViewClient(new WebViewClient() {

            // 设置不用系统浏览器打开,直接显示在当前 webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如果不是http或者https开头的url，那么使用手机自带的浏览器打开
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                }
                view.loadUrl(url);
                return false;
//                return super.shouldOverrideUrlLoading(view, url);
            }


            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
        });


    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        this.binding.webview.onResume();
        this.binding.webview.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.binding.webview.onPause();
        this.binding.webview.getSettings().setLightTouchEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.binding.webview != null) {
            this.binding.webview.destroy();
        }
    }
}
