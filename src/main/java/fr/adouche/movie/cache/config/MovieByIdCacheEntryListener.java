package fr.adouche.movie.cache.config;

import java.util.Objects;
import java.util.stream.StreamSupport;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;
import javax.cache.event.CacheEntryUpdatedListener;
import javax.enterprise.inject.spi.CDI;

import fr.adouche.movie.cache.enums.CachesName;

public class MovieByIdCacheEntryListener implements CacheEntryCreatedListener<Object, Object>,
        CacheEntryUpdatedListener<Object, Object>, CacheEntryExpiredListener<Object, Object>,
        CacheEntryRemovedListener<Object, Object> {

    CacheManager cacheManager;

    public MovieByIdCacheEntryListener() {
        cacheManager = CDI.current().select(CacheManager.class).get();
    }

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(this::display);
        clearOtherCache();
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(this::display);
        clearOtherCache();
    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(this::display);
        clearOtherCache();
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(this::display);
    }

    private void display(CacheEntryEvent<? extends Object, ? extends Object> event) {
        System.out.println(
                String.format("[cache %s][listener %s][%s] - %s=%s)",
                        event.getSource().getName(),
                        this,
                        event.getEventType(),
                        event.getKey(),
                        event.getValue()));
    }

    private void clearOtherCache() {
        StreamSupport.stream(cacheManager.getCacheNames().spliterator(), false)
                .filter(name -> !name.equals(CachesName.MOVIE_BY_ID.getCacheName()))
                .map(cacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear); //ne pas utiliser removeAll sinon plantage. avec clear on ne notifie pas les listener, avec removeAll oui.
    }
}
