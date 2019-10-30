package com.fffz.mvvm.viewmodel;

import com.fffz.mvvm.data.DataProvider;

public class BaseViewModel {

    public void destroy() {
        DataProvider.get().cancel(this);
    }
}
