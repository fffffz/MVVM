package com.fffz.mvvm;

import android.support.v7.app.AppCompatActivity;

import com.kelin.mvvmlight.messenger.Messenger;

import com.fffz.mvvm.data.DataProvider;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        DataProvider.get().cancel(this);
        Messenger.getDefault().unregister(this);
//        IMMLeaks.fixFocusedViewLeak(getApplication());
        super.onDestroy();
    }
}
