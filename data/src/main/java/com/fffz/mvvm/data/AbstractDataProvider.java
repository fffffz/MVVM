package com.fffz.mvvm.data;

import com.fffz.mvvm.data.webservice.WebService;
import com.fffz.mvvm.data.webservice.WebServiceProvider;
import com.fffz.mvvm.data.model.News;
import com.fffz.mvvm.data.model.TopNews;

import java.util.Map;
import java.util.WeakHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

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
