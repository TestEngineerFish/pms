package com.einyun.app.base.http;


import android.util.Log;
import com.einyun.app.base.http.constants.HttpConfigConstant;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Http封装
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
public class HttpService {
    protected final String TAG="HttpService";
    /**
     * 基础业务地址
     */
    protected static String BASE_URL = "https://smcloud.shimaowy.com";

    protected static HttpService netWorkManager;
    protected static boolean DEBUG= BuildConfig.DEBUG;
    protected static Map<String,String> headMap =new HashMap<>();
    protected static Map<String,String> filter =new HashMap<>();
    protected  ServiceApi serviceApi;

    public String getBaseUrl(){
        return BASE_URL;
    }
    public static HttpService getInstance() {F
        if(netWorkManager==null){
            synchronized (HttpService.class){
                if (netWorkManager == null) {
                    netWorkManager = new HttpService();
                }
            }
        }
        return netWorkManager;
    }

    protected HttpService() {
    }

    public ServiceApi getServiceApi() {
        if (serviceApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(ResponseConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(createOkHttpClient())
                    .build();
            serviceApi = retrofit.create(ServiceApi.class);
        }

        return serviceApi;
    }

    /**
     * 自定义service
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getServiceApi(Class cls) {
        T api;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
        api = (T) retrofit.create(cls);

        return api;
    }

    /**
     * 自定义service,自定义url
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getServiceApi(String url,Class cls) {
        T api;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
        api = (T) retrofit.create(cls);

        return api;
    }



    protected OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        //设置超时时间
        clientBuilder.connectTimeout(HttpConfigConstant.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(HttpConfigConstant.READ_TIME_OUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(HttpConfigConstant.WRITE_TIME_OUT, TimeUnit.SECONDS);

        //日志处理
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String s) {
                if(DEBUG){
                    Log.d(TAG,s);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        /************************** https ，没有证书时 **********************************/
        clientBuilder.sslSocketFactory(HttpsTrustManager.createSSLSocketFactory());
        clientBuilder.hostnameVerifier(new HttpsTrustManager.TrustAllHostnameVerifier());

        /***********************************************************/


        //基础参数
        Interceptor paramsInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                //旧request
                Request oldRequest = chain.request();
                //拿到拥有以前的request里的url的那些信息的builder
                HttpUrl.Builder builder = oldRequest
                        .url()
                        .newBuilder();
                //得到新的url（已经追加好了参数）

                HttpUrl newUrl = builder.build();
                //利用新的Url，构建新的request，并发送给服务器
                Request.Builder requestBuilder =oldRequest
                        .newBuilder();
                if(headMap!=null&&headMap.size()>0){
                    for(String key:headMap.keySet()){
                        String value=headMap.get(key);
                        requestBuilder.addHeader(key,value);
                    }
                }
                filter(newUrl.url().toString());
//                if (!TextUtils.isEmpty(token)) {
//                    requestBuilder.addHeader("Authorization", "Bearer " + token);
//                }
                Request newRequest = requestBuilder
                        .url(newUrl)
                        .build();
                return chain.proceed(newRequest);
            }
        };
        clientBuilder.addInterceptor(paramsInterceptor);

        //缓存处理

        return clientBuilder.build();
    }

    protected void filter(String url){
    }

    protected void addFilter(String key,String value){
        this.filter.put(key,value);
    }


    public void addHeader(String key,String value){
        this.headMap.put(key,value);
    }
}
