package fr.adouche.movie.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.cache.annotation.CacheKey;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import fr.adouche.movie.entity.Movie;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class MovieRepository {

    @PersistenceContext(unitName = "movie-unit")
    private EntityManager entityManager;

    public Movie find(final Long id) {
        return entityManager.find(Movie.class, id);
    }

    public void addMovie(final Movie movie) {
        entityManager.persist(movie);
    }

    public void editMovie(final Movie movie) {
        entityManager.merge(movie);
    }

    public void deleteMovie(final Long id) {
        final Movie movie = entityManager.find(Movie.class, id);
        entityManager.remove(movie);
    }

    @SuppressWarnings("unchecked")
    public List<Movie> getMovies() {
        return entityManager.createNativeQuery("select * from movie", Movie.class).getResultList();
    }

    public List<Movie> getMovies(final Integer firstResult, final Integer maxResults, final String field, final String searchTerm) {
        final CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Movie> cq = qb.createQuery(Movie.class);
        final Root<Movie> root = cq.from(Movie.class);
        final EntityType<Movie> type = entityManager.getMetamodel().entity(Movie.class);

        addConditionToCriteriaQuery(field, searchTerm, qb, cq, root, type);

        cq.orderBy(qb.desc(root.get("productionYear")), qb.asc(root.get("title")));

        final TypedQuery<Movie> q = entityManager.createQuery(cq);
        if (maxResults != null) {
            q.setMaxResults(maxResults);
        }
        if (firstResult != null) {
            q.setFirstResult(firstResult);
        }

        return q.getResultList();
    }



    public int count(final String field, final String searchTerm) {
        final CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        final Root<Movie> root = cq.from(Movie.class);
        final EntityType<Movie> type = entityManager.getMetamodel().entity(Movie.class);

        cq.select(qb.count(root));
        addConditionToCriteriaQuery(field, searchTerm, qb, cq, root, type);

        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public Collection<String> getGenres() {
        final Set<String> result = new HashSet<>();

        final CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<String> cq = qb.createQuery(String.class);
        final Root<Movie> root = cq.from(Movie.class);
        cq.select(root.<String> get("genre")).distinct(true);
        final TypedQuery<String> q = entityManager.createQuery(cq);
        final List<String> resultList = q.getResultList();
        for (final String r : resultList) {
            final String[] genres = r.split(" *, *");
            Collections.addAll(result, genres);
        }

        return result;
    }

    private void addConditionToCriteriaQuery(String field, String searchTerm, CriteriaBuilder qb, CriteriaQuery<?> cq, Root<Movie> root, EntityType<Movie> type) {
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(searchTerm)) {
            final Path<String> path = root.get(type.getDeclaredSingularAttribute(field.trim(), String.class));

            final Predicate condition = ("rating".equals(field) || "productionYear".equals(field))
                    ? qb.equal(path, Integer.parseInt(searchTerm.trim()))
                    : qb.like(path, "%" + searchTerm.trim() + "%");
            cq.where(condition);
        }
    }
}
