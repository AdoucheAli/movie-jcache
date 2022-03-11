package fr.adouche.movie.cache.config;

import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryListener;

//used hazelcast.xml
public class MovieCacheEntryListenerFactory implements Factory<CacheEntryListener<Object, Object>> {

    /**
     *
     */
    private static final long serialVersionUID = -3020998279546151677L;

    @Override
    public CacheEntryListener<Object, Object> create() {
        return new MovieCacheEntryListener();
    }
}
