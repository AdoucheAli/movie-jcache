package fr.adouche.movie.cache.enums;

public enum FullyQualifiedProviderName {

    JBOSS_DATA_GRID(""), HAZELCAST("com.hazelcast.cache.HazelcastCachingProvider");

    FullyQualifiedProviderName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    private String fullyQualifiedName;

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }
}
