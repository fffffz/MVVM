package com.fffz.mvvm.data;

public class DataProvider extends AbstractDataProvider {

    private static class DataProviderHolder {
        private static final DataProvider INSTANCE = new DataProvider();
    }

    public static DataProvider get() {
        return DataProviderHolder.INSTANCE;
    }

    private DataProvider() {
    }

    public AbstractDataProvider tag(Object target) {
        AbstractDataProvider provider = providerMap.get(target);
        if (provider == null) {
            provider = new DataProvider();
            providerMap.put(target, provider);
        }
        return provider;
    }

}
