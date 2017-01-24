package org.icegeneral.mvvm.data.internal.webservice;

import org.icegeneral.mvvm.data.DiskCache;
import org.icegeneral.mvvm.data.utils.DigestUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by jianjun.lin on 2017/1/12.
 */

public class WebServiceInvocationHandler implements InvocationHandler {

    private WebService actualWebService;

    public WebServiceInvocationHandler(WebService actualWebService) {
        this.actualWebService = actualWebService;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        final String key = getKey(method, args);
        final Observable actualObservable = (Observable) method.invoke(actualWebService, args);
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> actualSubscriber) {
                actualObservable
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof IOException) {
                                    Object o = DiskCache.getInstance().get(key);
                                    if (o != null) {
                                        actualSubscriber.onNext(o);
                                        actualSubscriber.onCompleted();
                                        return;
                                    }
                                }
                                actualSubscriber.onError(e);
                            }

                            @Override
                            public void onNext(Object o) {
                                if (o instanceof Serializable) {
                                    DiskCache.getInstance().put(key, (Serializable) o);
                                }
                                actualSubscriber.onNext(o);
                                actualSubscriber.onCompleted();
                            }
                        });
            }
        });
    }

    private String getKey(Method method, Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName());
        if (args != null) {
            for (Object arg : args) {
                sb.append("-").append(arg.toString());
            }
        }
        return DigestUtils.md5DigestAsHex(sb.toString());
    }

}
