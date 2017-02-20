package org.icegeneral.mvvm.data;

import org.icegeneral.mvvm.data.internal.webservice.WebService;
import org.icegeneral.mvvm.data.internal.webservice.WebServiceProvider;
import org.icegeneral.mvvm.data.model.News;
import org.icegeneral.mvvm.data.model.TopNews;

import java.util.Map;
import java.util.WeakHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianjun.lin on 2017/2/17.
 */

public abstract class AbstractDataProvider implements WebService {

    protected Map<Object, AbstractDataProvider> providerMap = new WeakHashMap<>();

    private CompositeSubscription subscription = new CompositeSubscription();

    public void cancel(Object target) {
        AbstractDataProvider provider = providerMap.get(target);
        if (provider == null) {
            return;
        }
        provider.cancel();
    }

    public void cancel() {
        subscription.unsubscribe();
        for (AbstractDataProvider provider : providerMap.values()) {
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
