package com.einyun.app.pms.notice.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.SerializableMap;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.ShareUtil;
import com.einyun.app.library.mdm.model.NoticeModel;
import com.einyun.app.pms.notice.R;
import com.einyun.app.pms.notice.databinding.ActivityNoticeDetailBinding;
import com.einyun.app.pms.notice.viewmodel.NoticeViewModel;
import com.einyun.app.pms.notice.viewmodel.ViewModelFactory;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

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
@Route(path = RouterUtils.ACTIVITY_NOTICE_DETAIL)
public class NoticeDetailActivity extends BaseHeadViewModelActivity<ActivityNoticeDetailBinding, NoticeViewModel> {
    @Autowired(name = RouteKey.KEY_ID)
    String id;

    @Autowired(name = RouteKey.KEY_WEB_TITLE)
    String webTitle;

    @Autowired(name = RouteKey.KEY_PARAMS)
    Bundle bundle;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    SerializableMap params;
    String webUrl;
    private WebSettings webSettings;// WebSettings对象
    NoticeModel model = new NoticeModel();
    Integer upDownState = 0;
    @Override
    protected NoticeViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this, new ViewModelFactory()).get(NoticeViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webview.canGoBack()) {
            binding.webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onOptionClick(View view) {
        super.onOptionClick(view);
        ShareUtil.share(this, model.getTitle(), "打开公告详情", webUrl);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setRightOption(R.mipmap.share);
        webUrl = DataConstants.NOTICE_DETAIL_URL + id + "&tenantId=" + CommonHttpService.getInstance().getTenantId();

        headBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (webTitle != null) {
            setHeadTitle(webTitle);
        }


        viewModel.noticeModelLiveData.observe(this, noticeModel -> {
            model = noticeModel;
            binding.tvGoodEvaultion.setText(String.valueOf(noticeModel.getThumbsUpNumber()));
            binding.tvBadEvaultion.setText(String.valueOf(noticeModel.getThumbsDownNumber()));
        });
        viewModel.queryUpDown(id).observe(this,s -> {
            if (upDownState == null){
                return;
            }
            upDownState = s;
            if (upDownState ==1){
                binding.ivGoodEvaultion.setImageResource(R.mipmap.icon_click_good_evaultion);
                binding.ivBadEvaultion.setImageResource(R.mipmap.icon_bad_evaultion);
            }else if (upDownState ==2){
                binding.ivGoodEvaultion.setImageResource(R.mipmap.icon_good_evaultion);
                binding.ivBadEvaultion.setImageResource(R.mipmap.icon_click_bad_evaultion);
            }
        });
        viewModel.addReading(id);
        binding.llGoodEvaultion.setOnClickListener(v -> {
            if (upDownState == 1){
                return;
            }
            upDownState = 1;
            viewModel.updateNoticeLikeBad(id, "1");
        });
        binding.llBadEvaultion.setOnClickListener(v -> {
            if (upDownState == 2){
                return;
            }
            upDownState = 2;
            viewModel.updateNoticeLikeBad(id, "2");
        });
        binding.headBar.ivBack.setImageDrawable(getResources().getDrawable(R.mipmap.back_black));
        binding.headBar.tvHeaderTitle.setTextColor(getResources().getColor(R.color.title_text_icon_color));
        binding.headBar.mainHeaderBar.setBackground(getResources().getDrawable(R.color.white));

        // 清除网页访问留下的缓存
        // 由于内核缓存是全局的因此这个方法不仅仅针对webView而是针对整个应用程序
//        binding.webview.clearCache(true);
        // 清除当前webView的访问历史记录
//        binding.webview.clearHistory();

        // 这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        binding.webview.clearFormData();
        // 获取WebSettings对象
        webSettings = binding.webview.getSettings();
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存，直接用网络加载
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 告诉js可以自动打开window
        // 两者一起使用，可以让html页面加载显示适应手机的屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setSavePassword(false);// 关闭密码保存提醒；该方法在以后的版本中该方法将不被支持
        webSettings.setDomStorageEnabled(true);// 设置支持DOM storage API
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        binding.webview.addJavascriptInterface(new Object(), "einyun");
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    @Override
    protected void initData() {
        super.initData();
        if (bundle != null) {
            params = (SerializableMap) bundle.get(RouteKey.KEY_MAP_SERIALIZABLE);
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
//                if (!url.startsWith("http://") && !url.startsWith("https://")) {
//                    try {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        startActivity(intent);
//                        return true;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return true;
//                    }
//                }
                view.loadUrl(url);
                return false;
//                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                return super.shouldInterceptRequest(webView, s);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
                return super.shouldInterceptRequest(webView, webResourceRequest, bundle);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }
        });

        if (params != null) {
            binding.webview.loadUrl(webUrl, params.getMap());
        } else {
            binding.webview.loadUrl(webUrl);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getNoticeDetail(id);
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
