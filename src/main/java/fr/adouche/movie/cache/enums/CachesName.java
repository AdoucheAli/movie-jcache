package fr.adouche.movie.cache.enums;

public enum CachesName {

    MOVIES("movies"), COUNT("count"), GENRE("genre"), MOVIE_BY_ID("movieById");

    String cacheName;

    CachesName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }
}
