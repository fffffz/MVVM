package com.fffz.mvvm;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fffz.mvvm.data.DiskCache;
import com.fffz.mvvm.data.DiskCacheConfiguration;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        Fresco.initialize(this);
        DiskCacheConfiguration diskCacheConfiguration = DiskCacheConfiguration.createDefault(getFilesDir());
        DiskCache.getInstance().init(diskCacheConfiguration);
    }
}
