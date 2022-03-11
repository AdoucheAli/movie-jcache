package fr.adouche.movie.cache.qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import fr.adouche.movie.cache.enums.CachesName;

@Qualifier
//@Target({TYPE, METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
@Documented
public @interface NamedCache {
    //Annotation avoid "Unsatisfied dependencies" error when injecting named cache.
    //argument is not considered for matching on the injection point
    @Nonbinding
    CachesName value() default CachesName.MOVIES;
}
