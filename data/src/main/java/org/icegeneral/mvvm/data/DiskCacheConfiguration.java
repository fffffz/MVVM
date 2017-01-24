package org.icegeneral.mvvm.data;

import java.io.File;

/**
 * Created by jianjun.lin on 2017/1/11.
 */

public class DiskCacheConfiguration {

    public final File cacheDir;
    //    public final int maxCacheFileCount;
    public final long maxCacheSize;

    private DiskCacheConfiguration(Builder builder) {
        cacheDir = builder.cacheDir;
//        maxCacheFileCount = builder.maxCacheFileCount;
        maxCacheSize = builder.maxCacheSize;
    }

    public static DiskCacheConfiguration createDefault(File cacheDir) {
        return new DiskCacheConfiguration(new Builder(cacheDir));
    }

    public static class Builder {

        private File cacheDir;
        private long maxCacheSize = 100 * 1024 * 1024;
//        private int maxCacheFileCount = 1000;

        public Builder(File cacheDir) {
            this.cacheDir = cacheDir;
        }

//        public Builder maxCacheFileCount(int cacheFileCount) {
//            this.maxCacheFileCount = cacheFileCount;
//            return this;
//        }

        public Builder maxCacheSize(long cacheSize) {
            this.maxCacheSize = cacheSize;
            return this;
        }

        public DiskCacheConfiguration build() {
            return new DiskCacheConfiguration(this);
        }

    }
}
