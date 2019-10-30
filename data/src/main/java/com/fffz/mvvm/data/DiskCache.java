package com.fffz.mvvm.data;

import com.fffz.mvvm.data.utils.DigestUtils;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DiskCache {

    private static class DiskCacheHolder {
        private static DiskCache INSTANCE = new DiskCache();
    }

    public static DiskCache getInstance() {
        return DiskCacheHolder.INSTANCE;
    }

    private DiskLruCache diskLruCache;

    private DiskCache() {
    }

    public void init(DiskCacheConfiguration configuration) {
        try {
            diskLruCache = DiskLruCache.open(configuration.cacheDir, BuildConfig.VERSION_CODE, 1, configuration.maxCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, Serializable obj) {
        key = DigestUtils.md5DigestAsHex(key);
        DiskLruCache.Editor editor = null;
        ObjectOutputStream oos = null;
        try {
            editor = diskLruCache.edit(key);
            oos = new ObjectOutputStream(editor.newOutputStream(0));
            oos.writeObject(obj);
            oos.close();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                oos.close();
            } catch (IOException e1) {
            }
            try {
                editor.abort();
            } catch (IOException e1) {
            }
        }
    }

    public synchronized <T> T get(String key) {
        key = DigestUtils.md5DigestAsHex(key);
        ObjectInputStream ois = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot == null) {
                return null;
            }
            ois = new ObjectInputStream(snapshot.getInputStream(0));
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
        }
    }

}
