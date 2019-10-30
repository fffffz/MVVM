package com.fffz.mvvm.viewmodel;

import android.databinding.ObservableField;
import android.util.Log;

import com.kelin.mvvmlight.command.ReplyCommand;

import com.fffz.mvvm.data.model.TopNews;

import rx.functions.Action0;

public class TopItemViewModel extends BaseViewModel {

    public TopNews.TopStoriesBean topStoriesBean;

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public ReplyCommand itemClickCommand = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            Log.d("TEST", topStoriesBean.getTitle());
        }
    });

    public TopItemViewModel(TopNews.TopStoriesBean topStoriesBean) {
        this.topStoriesBean = topStoriesBean;
        title.set(topStoriesBean.getTitle());
        imageUrl.set(topStoriesBean.getImage());
    }

}
