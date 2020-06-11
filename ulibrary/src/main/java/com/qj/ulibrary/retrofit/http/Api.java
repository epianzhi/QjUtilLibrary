package com.qj.ulibrary.retrofit.http;


import com.qj.ulibrary.retrofit.util.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */

public class Api {
    private static ApiService SERVICE;
    public static Retrofit retrofit;

    public static String baseUrl;

    public Api(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 30000;

    public static Retrofit getDefault(String baseUrl) {
        if (SERVICE == null) {
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.addInterceptor(new LogInterceptor());
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            /**
             *  拦截器
             */
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

//                    Request.Builder requestBuilder = request.newBuilder();
//                    RequestBody formBody = new FormBody.Builder()
//                            .add("1","2")
//                            .build();

                    HttpUrl.Builder authorizedUrlBuilder = request.url()
                            .newBuilder()
                            //添加统一参数 如手机唯一标识符,token等
//                            .addQueryParameter("key1","value1")
//                            .addQueryParameter("key2", "value2")
                            ;

                    Request newRequest = request.newBuilder()
                            //对所有请求添加请求头
//                            .header("mobileFlag", "adfsaeefe").addHeader("type", "4")
                            .method(request.method(), request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();

//                    okhttp3.Response originalResponse = chain.proceed(request);
//                    return2 originalResponse.newBuilder().header("mobileFlag", "adfsaeefe").addHeader("type", "4").build();
                    return chain.proceed(newRequest);
                }
            });


            retrofit = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }

    public static class LogInterceptor implements Interceptor {

        public String TAG = "Interceptor";

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            okhttp3.Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogUtils.d(TAG, "\n");
            LogUtils.d(TAG, "----------Start----------------");
            LogUtils.d(TAG, "| " + request.toString());
            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    LogUtils.d(TAG, "| RequestParams:{" + sb.toString() + "}");
                }
            }
            LogUtils.d(TAG, "| Response:" + content);
            LogUtils.d(TAG, "----------End:" + duration + "毫秒----------");
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }

}
