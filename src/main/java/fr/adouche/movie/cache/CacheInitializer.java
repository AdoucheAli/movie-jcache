package fr.adouche.movie.cache;

import static fr.adouche.movie.cache.config.CacheConfig.geDefaultConfig;
import static fr.adouche.movie.cache.enums.FullyQualifiedProviderName.HAZELCAST;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.hazelcast.core.Hazelcast;

import fr.adouche.movie.cache.config.CacheConfig;
import fr.adouche.movie.cache.config.MovieByIdCacheEntryListener;

@ApplicationScoped
public class CacheInitializer {

    private CachingProvider cachingProvider;

    private CacheManager cacheManager;

    @Inject
    Event<CacheManager> cacheManagerEvent;

    public void boot(@Observes @Initialized(ApplicationScoped.class) Object doesntMatter) {

        /*
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            URI uri = contextClassLoader.getResource("hazelcast.xml").toURI();

            to avoid error [com.hazelcast.internal.util.XmlUtil] Enabling XXE protection failed
            we put a property in file hazelcastXXE.properties and we load it in SystemPropertiesLoader

             start the default CacheManager -- it locates the default hazelcast.xml configuration
             and identify the existing HazelcastInstance by its name
         */
        Hazelcast.newHazelcastInstance();

        //this.cachingProvider = Caching.getCachingProvider(HAZELCAST.getFullyQualifiedName(), contextClassLoader);
        //this.cacheManager = cachingProvider.getCacheManager(uri, contextClassLoader);

        this.cachingProvider = Caching.getCachingProvider(HAZELCAST.getFullyQualifiedName());
        this.cacheManager = cachingProvider.getCacheManager();

        //cache movies initialized in hazelcast.xml file
        cacheManager.getCache("movies"); //otherwise, movies don't appear when we call cachemanager.getCacheNames

        createNewCaches();

        cacheManagerEvent.fire(cacheManager);

    }

    private void createNewCaches() {
        cacheManager.createCache("count", geDefaultConfig(TimeUnit.MINUTES, 30));

        cacheManager.createCache("movieById", geDefaultConfig(TimeUnit.SECONDS, 5))
                .registerCacheEntryListener(CacheConfig.getConfigCacheEntryListener(MovieByIdCacheEntryListener.class));
    }

    @Produces
    public CacheManager getCacheManager() {
        return this.cacheManager;
    }

    @Produces
    public CachingProvider getHazelcastCacheProvider() {
        return this.cachingProvider;
    }

    @PreDestroy
    public void shutdown() {
        this.cacheManager.close();
        this.cachingProvider.close();
    }
}
