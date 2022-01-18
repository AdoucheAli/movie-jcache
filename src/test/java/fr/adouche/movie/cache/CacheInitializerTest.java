package fr.adouche.movie.cache;

import static fr.adouche.movie.cache.enums.FullyQualifiedProviderName.HAZELCAST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.spi.CachingProvider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;

@Disabled
class CacheInitializerTest {

    private static CacheManager cacheManager;

    private static CachingProvider cachingProvider;

    private static Cache<String, String> cache;

    private static CompleteConfiguration<String, String> configuration;

    @BeforeAll
    static void init() {
        Hazelcast.newHazelcastInstance();
        cachingProvider = Caching.getCachingProvider(HAZELCAST.getFullyQualifiedName());
        cacheManager = cachingProvider.getCacheManager();
        cache = cacheManager.getCache("test");
        configuration = cache.getConfiguration(CompleteConfiguration.class);
    }

    @Test
    void testProviderAndManagerNotNull() {
        assertNotNull(cachingProvider);
        assertNotNull(cacheManager);

    }

    @Test
    void testCacheNotNull() {
        assertNotNull(cache);

    }

    @Test
    void testCacheManagementAndStatisticsEnabled() {

        assertTrue(configuration.isManagementEnabled());
        assertTrue(configuration.isStatisticsEnabled());

    }

    @Test
    void testCacheExpiryPolicy() throws InterruptedException {

        cache.put("France", "Paris");
        assertEquals(cache.get("France"), "Paris");

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

        cache.put("Belgique", "Bruxelles");
        assertEquals(cache.get("Belgique"), "Bruxelles");

        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

        assertNull(cache.get("France"));
        assertNotNull(cache.get("Belgique"));

        Thread.sleep(TimeUnit.SECONDS.toMillis(10));

        assertNull(cache.get("Belgique"));

    }

    @AfterAll
    static void destroy() {
        cachingProvider.close();
        cacheManager.close();
    }
}
