package org.icegeneral.mvvm.data;

import org.icegeneral.mvvm.data.internal.webservice.WebServiceProvider;
import org.icegeneral.mvvm.data.model.News;
import org.icegeneral.mvvm.data.model.TopNews;

import java.util.Map;
import java.util.WeakHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianjun.lin on 2017/1/7.
 */

public class DataProvider extends AbstractDataProvider {

    private static class DataProviderHolder {
        private static final DataProvider INSTANCE = new DataProvider();
    }

    public static DataProvider get() {
        return DataProviderHolder.INSTANCE;
    }

    private Map<Object, DataProvider> providerMap = new WeakHashMap<>();
    private CompositeSubscription subscription = new CompositeSubscription();

    private DataProvider() {
    }

    public AbstractDataProvider tag(Object target) {
        DataProvider provider = providerMap.get(target);
        if (provider == null) {
            provider = new DataProvider();
            providerMap.put(target, provider);
        }
        return provider;
    }

    public void cancel(Object target) {
        DataProvider provider = providerMap.get(target);
        if (provider == null) {
            return;
        }
        provider.cancel();
    }

    public void cancel() {
        subscription.unsubscribe();
        for (DataProvider provider : providerMap.values()) {
            provider.cancel();
        }
    }

    private <T> Observable.Operator<T, T> bind() {
        return new Observable.Operator<T, T>() {
            @Override
            public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
                subscription.add(subscriber);
                return subscriber;
            }
        };
    }

    @Override
    public Observable<News> getNewsList(String date) {
        return WebServiceProvider.get().getNewsList(date).lift(this.<News>bind());
    }

    @Override
    public Observable<TopNews> getTopNewsList() {
        return WebServiceProvider.get().getTopNewsList().lift(this.<TopNews>bind());
    }

}
