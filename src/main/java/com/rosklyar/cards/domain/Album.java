package com.rosklyar.cards.domain;

import java.util.Objects;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * Created by rostyslavs on 11/21/2015.
 */
public class Album {

    public final long id;
    public final String name;
    public final Set<AlbumSet> sets;

    public Album(long id, String name, Set<AlbumSet> sets) {
        this.id = id;
        this.name = name;
        this.sets = sets;
    }

    public Album getEmptyAlbum(){

        Set<AlbumSet> emptySets = newHashSet();
        sets.forEach(set -> emptySets.add(set.getEmptyCopy()));
        return new Album(id, name, emptySets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id &&
                Objects.equals(name, album.name) &&
                Objects.equals(sets, album.sets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sets);
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
