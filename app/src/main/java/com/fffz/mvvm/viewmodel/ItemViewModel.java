package com.fffz.mvvm.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.kelin.mvvmlight.command.ReplyCommand;

import com.fffz.mvvm.R;
import com.fffz.mvvm.data.model.News;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.functions.Action0;

public class ItemViewModel extends BaseViewModel {

    public final ItemView topItemView = ItemView.of(com.fffz.mvvm.BR.viewModel, R.layout.viewpager_item_top_news);
    public final ObservableList<TopItemViewModel> topItemViewModels = new ObservableArrayList<>();

    public News.StoriesBean storiesBean;

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();

    public ReplyCommand itemClickCommand = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            Log.d("TEST", storiesBean.getTitle());
        }
    });

    public ItemViewModel() {
    }

    public ItemViewModel(News.StoriesBean storiesBean) {
        this.storiesBean = storiesBean;
        if (storiesBean.isHeader()) {
            date.set(storiesBean.getDate());
        } else {
            title.set(storiesBean.getTitle());
            imageUrl.set(storiesBean.getImages().get(0));
        }
    }

}
