package com.fffz.mvvm.data.webservice;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WebServiceInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        long t1 = System.nanoTime();
        Log.d(getClass().getSimpleName(), String.format("Send request %s%n%s",
                request.url(), request.headers()));
        response = chain.proceed(request);
        MediaType contentType = response.body().contentType();
        String body = response.body().string();
        long t2 = System.nanoTime();
        Log.d(getClass().getSimpleName(), String.format("Receive response for %s in %.1fms%n%s%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(), body));
        ResponseBody responseBody = ResponseBody.create(contentType, body);
        response = response.newBuilder().body(responseBody).build();
        return response;
    }
}