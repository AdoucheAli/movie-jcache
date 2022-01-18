package fr.adouche.movie.cache.config;

import javax.cache.annotation.GeneratedCacheKey;

class LongCacheKey implements GeneratedCacheKey {

    /**
     *
     */
    private static final long serialVersionUID = 4603141756438871677L;

    private Long value;

    public LongCacheKey(Long param) {
        this.value = param;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.value.equals(((LongCacheKey) obj).getValue());
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "LongCacheKey [value=" + value + "]";
    }
}
