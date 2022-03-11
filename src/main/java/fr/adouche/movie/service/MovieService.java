package fr.adouche.movie.service;

import java.util.Collection;
import java.util.List;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.adouche.movie.cache.config.MovieIdCacheKeyGenerator;
import fr.adouche.movie.entity.Movie;
import fr.adouche.movie.interceptor.Timing;
import fr.adouche.movie.repository.MovieRepository;

@Stateless
@Timing
@CacheDefaults(cacheName = "movies")
public class MovieService {

    @Inject
    MovieRepository repository;

    @CacheResult
    public Movie find(final @CacheKey Long id) {
        return repository.find(id);
    }

    @CachePut(cacheName = "movieById", cacheKeyGenerator = MovieIdCacheKeyGenerator.class)
    public void addMovie(final @CacheValue Movie movie) {
        repository.addMovie(movie);
    }

    @CachePut(cacheName = "movieById", cacheKeyGenerator = MovieIdCacheKeyGenerator.class)
    public void editMovie(final @CacheValue Movie movie) {
        repository.editMovie(movie);
    }

    @CacheRemove(cacheName = "movieById")
    public void deleteMovie(final @CacheKey Long id) {
        repository.deleteMovie(id);
    }

    @CacheResult
    @Timing
    public List<Movie> getMovies() {
        return repository.getMovies();
    }

    @CacheResult
    @Timing
    public List<Movie> getMovies(final Integer firstResult, final Integer maxResults, final String field, final String searchTerm) {
        return repository.getMovies(firstResult, maxResults, field, searchTerm);
    }

    @CacheResult(cacheName = "count")
    public int count(final String field, final String searchTerm) {
        return repository.count(field, searchTerm);
    }

    @CacheResult(cacheName = "genre")
    public Collection<String> getGenres() {
        return repository.getGenres();
    }
}
