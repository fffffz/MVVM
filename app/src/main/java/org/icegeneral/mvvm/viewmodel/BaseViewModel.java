package org.icegeneral.mvvm.viewmodel;

import org.icegeneral.mvvm.data.DataProvider;

/**
 * Created by jianjun.lin on 2017/1/9.
 */

public class BaseViewModel {

    public void destroy() {
        DataProvider.get().cancel(this);
    }
}
