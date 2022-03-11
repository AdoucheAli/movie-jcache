package fr.adouche.movie.cache.config;

import java.lang.annotation.Annotation;

import javax.cache.annotation.CacheInvocationParameter;
import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheKeyInvocationContext;
import javax.cache.annotation.GeneratedCacheKey;

import org.jsr107.ri.annotations.DefaultGeneratedCacheKey;

import fr.adouche.movie.entity.Movie;

public class MovieIdCacheKeyGenerator implements CacheKeyGenerator {

    //When we save a movie we want to use his id as key
    @Override
    public GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext) {
        CacheInvocationParameter[] keyParameters = cacheKeyInvocationContext.getAllParameters();
        for (CacheInvocationParameter keyParameter : keyParameters) {
            if (keyParameter.getRawType().equals(Movie.class)) {
                Movie movie = Movie.class.cast(keyParameter.getValue());

                return new DefaultGeneratedCacheKey(new Object[] {movie.getId()});
                //return new LongCacheKey(movie.getId());
            }
        }
        throw new IllegalArgumentException("No movie argument found in method signature");
    }
}
