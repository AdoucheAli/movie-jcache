package fr.adouche.movie.cache.config;

import java.util.concurrent.TimeUnit;

import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;

public final class CacheConfig {

    private CacheConfig() {
    }

    private static final boolean IS_OLD_VALUE_REQUIRED = false;

    private static final boolean IS_SYNCHRONOUS = true;

    private static final Factory<? extends CacheEntryEventFilter<?, ?>> NO_FILTER = null;

    public static Configuration<Object, Object> geDefaultConfig() {
        return geDefaultConfig(Object.class, Object.class, TimeUnit.SECONDS, 10);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static CacheEntryListenerConfiguration getDefaultConfigCacheEntryListener() {
        return new MutableCacheEntryListenerConfiguration(
                FactoryBuilder.factoryOf(MovieCacheEntryListener.class),
                NO_FILTER,
                IS_OLD_VALUE_REQUIRED,
                IS_SYNCHRONOUS);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static CacheEntryListenerConfiguration getConfigCacheEntryListener(Class listenerType) {
        return new MutableCacheEntryListenerConfiguration(
                FactoryBuilder.factoryOf(listenerType),
                NO_FILTER,
                IS_OLD_VALUE_REQUIRED,
                IS_SYNCHRONOUS);
    }

    public static <K, V> Configuration<K, V> geDefaultConfig(Class<K> keyType, Class<V> valueType, TimeUnit expiryPolicyTimeUnit,
            int expiryPolicyDurationAmount) {
        MutableConfiguration<K, V> config = new MutableConfiguration<>();

        config.setTypes(keyType, valueType);
        config.setStoreByValue(Boolean.TRUE);
        config.setStatisticsEnabled(Boolean.TRUE);
        config.setManagementEnabled(Boolean.TRUE);
        config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(expiryPolicyTimeUnit, expiryPolicyDurationAmount)));

        return config;
    }

    public static Configuration<Object, Object> geDefaultConfig(TimeUnit expiryPolicyTimeUnit, int expiryPolicyDurationAmount) {
        return geDefaultConfig(Object.class, Object.class, expiryPolicyTimeUnit, expiryPolicyDurationAmount);
    }
}
