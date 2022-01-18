package fr.adouche.movie.cache;

import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import fr.adouche.movie.cache.qualifier.NamedCache;
import fr.adouche.movie.cache.utils.CacheUtils;

@ApplicationScoped
public class CacheProducer {

    @Inject
    CacheUtils cacheUtils;

    @Produces
    @NamedCache
    public Cache<Object, Object> createCache(InjectionPoint ip) {
        String cacheName = ip.getAnnotated().getAnnotation(NamedCache.class).value().getCacheName();

        return cacheUtils.getCache(cacheName)
                .orElseGet(() -> cacheUtils.createCache(cacheName));
    }
}
