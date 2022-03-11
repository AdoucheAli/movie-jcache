package fr.adouche.movie.cache.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import fr.adouche.movie.cache.CacheInitializer;
import fr.adouche.movie.cache.config.CacheConfig;

@ApplicationScoped
public class CacheUtils {

    @Inject
    CacheInitializer initializer;

    public List<String> cacheNames() {
        Iterable<String> names = getCacheManager().getCacheNames();
        return StreamSupport.stream(names.spliterator(), false)
                .collect(Collectors.toList());
    }

    public boolean cacheExists(String name) {
        return cacheNames().contains(name);
    }

    public Optional<Cache<Object, Object>> getCache(String cacheName) {
        return Optional.ofNullable(getCacheManager().getCache(cacheName));
    }

    public Cache<Object, Object> createCache(String cacheName) {
        if (cacheExists(cacheName)) {
            return getCacheManager().getCache(cacheName);
        }

        return getCacheManager().createCache(cacheName, CacheConfig.geDefaultConfig());
    }

    private CacheManager getCacheManager() {
        return initializer.getCacheManager();
    }

    public void clearCache(String cacheName) {
        getCache(cacheName)
                .ifPresent(Cache::removeAll);
    }

    public void clearCaches() {
        cacheNames().forEach(this::clearCache);
    }
}
