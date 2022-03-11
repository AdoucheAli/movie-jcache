package fr.adouche.movie;

import javax.cache.CacheManager;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import fr.adouche.movie.entity.Movie;
import fr.adouche.movie.service.MovieService;

@Singleton
public class InitDbMovie {

    @Inject
    private MovieService service; //We can use MovieRepository instead of waiting cachemanager creation

    public void load() {
        service.addMovie(new Movie("Wedding Crashers", "David Dobkin", "Comedy", 7, 2005));
        service.addMovie(new Movie("Starsky & Hutch", "Todd Phillips", "Action", 6, 2004));
        service.addMovie(new Movie("Shanghai Knights", "David Dobkin", "Action", 6, 2003));
        service.addMovie(new Movie("I-Spy", "Betty Thomas", "Adventure", 5, 2002));
        service.addMovie(new Movie("The Royal Tenenbaums", "Wes Anderson", "Comedy", 8, 2001));
        service.addMovie(new Movie("Zoolander", "Ben Stiller", "Comedy", 6, 2001));
        service.addMovie(new Movie("Shanghai Noon", "Tom Dey", "Comedy", 7, 2000));
    }

    public void afterCacheManagerCreated(@Observes CacheManager cacheManager) {
        load();
    }
}
