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
    //avec cette annotation on n'aura pas d'erreur "Unsatisfied dependencies" lors de l'injection d'un cache avec une valeur particuliere.
    //le conteneur cdi ne tiendra pas compte de la value mais on pourras l'utiliser dans la methode qui produit le bean
    @Nonbinding
    CachesName value() default CachesName.MOVIES; //argument is not considered for matching on the injection point.
}
