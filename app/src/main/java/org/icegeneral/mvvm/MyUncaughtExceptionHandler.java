package org.icegeneral.mvvm;

/**
 * Created by jianjun.lin on 2017/1/12.
 */

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
    }
}
