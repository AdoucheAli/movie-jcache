package fr.adouche.movie.cache.config;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;
import javax.cache.event.CacheEntryUpdatedListener;

public class MovieCacheEntryListener implements CacheEntryCreatedListener<Object, Object>,
        CacheEntryUpdatedListener<Object, Object>, CacheEntryExpiredListener<Object, Object>,
        CacheEntryRemovedListener<Object, Object> {

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(entry -> display(entry, "created"));
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(entry -> display(entry, "Updated"));
    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(entry -> display(entry, "Removed"));
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> cacheEntryEvents) throws CacheEntryListenerException {
        cacheEntryEvents.forEach(entry -> display(entry, "Expired"));
    }

    private void display(CacheEntryEvent<? extends Object, ? extends Object> event, String startMessage) {
        System.out.println(
                String.format("[cache %s][listener %s][%s] - %s=%s)",
                        event.getSource().getName(),
                        this,
                        event.getEventType(),
                        event.getKey(),
                        event.getValue()));
    }
}
