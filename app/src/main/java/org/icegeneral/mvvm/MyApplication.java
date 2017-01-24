package org.icegeneral.mvvm;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.icegeneral.mvvm.data.DiskCache;
import org.icegeneral.mvvm.data.DiskCacheConfiguration;

/**
 * Created by jianjun.lin on 2017/1/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        Fresco.initialize(this);
        DiskCacheConfiguration diskCacheConfiguration = DiskCacheConfiguration.createDefault(getFilesDir());
        DiskCache.getInstance().init(diskCacheConfiguration);
    }
}
