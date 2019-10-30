package com.fffz.mvvm.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.kelin.mvvmlight.command.ReplyCommand;
import com.kelin.mvvmlight.messenger.Messenger;

import com.fffz.mvvm.R;
import com.fffz.mvvm.data.DataProvider;
import com.fffz.mvvm.data.model.News;
import com.fffz.mvvm.data.model.TopNews;
import com.fffz.mvvm.utils.DateUtils;

import java.util.Calendar;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class MainViewModel extends BaseViewModel {

    public static final String TOKEN_UPDATE_INDICATOR = MainViewModel.class.getName() + ".TOKEN_UPDATE_INDICATOR";

    public final ItemViewSelector<ItemViewModel> itemView = new BaseItemViewSelector<ItemViewModel>() {
        @Override
        public void select(ItemView itemView, int position, ItemViewModel itemViewModel) {
            if (position == 0) {
                itemView.set(com.fffz.mvvm.BR.viewModel, R.layout.list_news_header);
            } else if (itemViewModel.storiesBean.isHeader()) {
                if (position == 1) {
                    itemViewModel.date.set("今日热闻");
                }
                itemView.set(com.fffz.mvvm.BR.viewModel, R.layout.listitem_news_header);
            } else {
                itemView.set(com.fffz.mvvm.BR.viewModel, R.layout.listitem_news);
            }
        }

        @Override
        public int viewTypeCount() {
            return 3;
        }

    };
    public final ObservableList<ItemViewModel> itemViewModels = new ObservableArrayList<>();

    public final ViewStyle viewStyle = new ViewStyle();

    public class ViewStyle {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(true);
    }

    public final ReplyCommand onRefreshCommand = new ReplyCommand<>(new Action0() {
        @Override
        public void call() {
            refresh();
        }
    });

    public final ReplyCommand<Integer> onLoadMoreCommand = new ReplyCommand<>(new Action1<Integer>() {
        @Override
        public void call(Integer integer) {
            loadMore();
        }
    });

    private Object target;
    private String lastDate;

    public MainViewModel(Object target) {
        this.target = target;
        refresh();
    }

    private void refresh() {
        viewStyle.isRefreshing.set(true);
        itemViewModels.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Observable.zip(
                DataProvider.get().tag(this).getTopNewsList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<TopNews, TopNews>() {
                            @Override
                            public TopNews call(TopNews topNews) {
                                if (topNews == null) {
                                    return null;
                                }
                                Messenger.getDefault().sendNoMsgToTargetWithToken(TOKEN_UPDATE_INDICATOR, target);
                                ItemViewModel headerItemViewModel = new ItemViewModel();
                                for (TopNews.TopStoriesBean bean : topNews.getTop_stories()) {
                                    headerItemViewModel.topItemViewModels.add(new TopItemViewModel(bean));
                                }
                                itemViewModels.add(0, headerItemViewModel);
                                return topNews;
                            }
                        }),
                DataProvider.get().tag(this).getNewsList(DateUtils.getDate(calendar))
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<News, News>() {
                            @Override
                            public News call(News news) {
                                if (news == null) {
                                    return null;
                                }
                                lastDate = news.getDate();
                                News.StoriesBean header = new News.StoriesBean();
                                header.setDate(DateUtils.getItemHeaderDate(news.getDate()));
                                header.setHeader(true);
                                news.getStories().add(0, header);
                                for (News.StoriesBean bean : news.getStories()) {
                                    itemViewModels.add(new ItemViewModel(bean));
                                }
                                return news;
                            }
                        }),
                new Func2<TopNews, News, Void>() {
                    @Override
                    public Void call(TopNews topNews, News news) {
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        viewStyle.isRefreshing.set(false);
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        viewStyle.isRefreshing.set(false);
                    }
                });
    }

    private void loadMore() {
        DataProvider.get().tag(this).getNewsList(lastDate)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<News, News>() {
                    @Override
                    public News call(News news) {
                        if (news == null) {
                            return null;
                        }
                        lastDate = news.getDate();
                        News.StoriesBean header = new News.StoriesBean();
                        header.setDate(DateUtils.getItemHeaderDate(news.getDate()));
                        header.setHeader(true);
                        news.getStories().add(0, header);
                        for (News.StoriesBean bean : news.getStories()) {
                            itemViewModels.add(new ItemViewModel(bean));
                        }
                        return news;
                    }
                }).subscribe();
    }

}
