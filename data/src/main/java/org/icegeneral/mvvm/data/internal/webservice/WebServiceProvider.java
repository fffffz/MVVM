package org.icegeneral.mvvm.data.internal.webservice;

import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianjun.lin on 2017/1/6.
 */

public class WebServiceProvider {

    private WebService webService;

    private WebServiceProvider() {
        OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(new WebServiceInterceptor())
                        .build();
        WebService actualWebService =
                new Retrofit.Builder()
                        .baseUrl(WebService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(client)
                        .build()
                        .create(WebService.class);
        webService =
                (WebService) Proxy.newProxyInstance(
                        WebService.class.getClassLoader(),
                        new Class[]{WebService.class},
                        new WebServiceInvocationHandler(actualWebService));
    }

    private static class WebServiceHolder {
        private static final WebService INSTANCE = new WebServiceProvider().webService;
    }

    public static WebService get() {
        return WebServiceHolder.INSTANCE;
    }


}
