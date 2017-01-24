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

public class DataProvider {

    private static class DataProviderHolder {
        private static final DataProvider INSTANCE = new DataProvider();
    }

    public static DataProvider get() {
        return DataProviderHolder.INSTANCE;
    }

    private Map<Object, TagDataProvider> tagDataProviderMap = new WeakHashMap<>();

    private DataProvider() {
    }

    public DataProvider tag(Object target) {
        TagDataProvider tagDataProvider = tagDataProviderMap.get(target);
        if (tagDataProvider == null) {
            tagDataProvider = new TagDataProvider();
            tagDataProviderMap.put(target, tagDataProvider);
        }
        return tagDataProvider;
    }

    public void cancel(Object target) {
        TagDataProvider tagDataProvider = tagDataProviderMap.get(target);
        if (tagDataProvider == null) {
            return;
        }
        tagDataProvider.cancel();
    }

    public Observable<News> getNewsList(String date) {
        return WebServiceProvider.get().getNewsList(date);
    }

    public Observable<TopNews> getTopNewsList() {
        return WebServiceProvider.get().getTopNewsList();
    }

    private static class TagDataProvider extends DataProvider {
        private CompositeSubscription compositeSubscription = new CompositeSubscription();

        public void cancel() {
            compositeSubscription.unsubscribe();
        }

        @Override
        public Observable<News> getNewsList(String date) {
            return super.getNewsList(date).lift(this.<News>bind());
        }

        @Override
        public Observable<TopNews> getTopNewsList() {
            return super.getTopNewsList().lift(this.<TopNews>bind());
        }

        private <T> Observable.Operator<T, T> bind() {
            return new Observable.Operator<T, T>() {
                @Override
                public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
                    compositeSubscription.add(subscriber);
                    return subscriber;
                }
            };
        }
    }

}
