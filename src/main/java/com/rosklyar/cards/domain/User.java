package com.rosklyar.cards.domain;

import com.rosklyar.cards.Albums;
import com.rosklyar.cards.DefaultConfiguration;

import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.rosklyar.cards.Albums.*;

public class User {
    private long id;
    private Album album;
    private boolean done;

    public User(long id, Album album) {
        this.id = id;
        this.album = album;
        done = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public synchronized Set<Event> offerCard(long cardId) {
        Album sampleAlbum = DefaultConfiguration.getConfigurationProvider().get();
        AlbumSet sampleAlbumSet = findAlbumSetByCardId(cardId, sampleAlbum);
        Card currentCard = findCardById(cardId, sampleAlbumSet);

        long cardSetId = sampleAlbumSet.id;

        AlbumSet userAlbumSet = findSetById(cardSetId, album);

        if (userAlbumSet.containsCard(cardId)){
            return Collections.emptySet();
        }
        userAlbumSet.cards.add(currentCard);
        Set<Event> events = newHashSet();
        checkIfEventsOccurred(cardSetId, events);
        return events;

    }

    private void checkIfEventsOccurred(long cardSetId, Set<Event> events) {
        boolean isSetFull = checkIfSetFull(this, cardSetId);
        boolean isAlbumFull = checkIfAlbumFull(this);


        if (isSetFull) {
            events.add(new Event(id, Event.Type.SET_FINISHED));
        }
        if (isAlbumFull) {
            events.add(new Event(id, Event.Type.ALBUM_FINISHED));
        }
    }

    public Album getAlbum() {
        return album;
    }

}
