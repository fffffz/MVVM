package org.icegeneral.mvvm;

import android.support.v7.app.AppCompatActivity;

import com.kelin.mvvmlight.messenger.Messenger;

import org.icegeneral.mvvm.data.DataProvider;
import org.icegeneral.mvvm.utils.IMMLeaks;

/**
 * Created by jianjun.lin on 2017/1/9.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        DataProvider.get().cancel(this);
        Messenger.getDefault().unregister(this);
//        IMMLeaks.fixFocusedViewLeak(getApplication());
        super.onDestroy();
    }
}
