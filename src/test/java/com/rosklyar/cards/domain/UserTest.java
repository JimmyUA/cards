package com.rosklyar.cards.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private int userId = 1;
    private User user;
    private Album album;

    @Before
    public void setUp() throws Exception {
        album = new Album(1L, "Animals", newHashSet(
                new AlbumSet(1L, "Birds", newHashSet(new Card(1L, "Eagle"), new Card(2L, "Cormorant"), new Card(3L, "Sparrow"), new Card(4L, "Raven"))),
                new AlbumSet(2L, "Fish", newHashSet(new Card(5L, "Salmon"), new Card(6L, "Mullet"), new Card(7L, "Bream"), new Card(8L, "Marline")))
        ));
        Album emptyAlbum = album.getEmptyAlbum();
        user = new User(userId, emptyAlbum);
    }

    @Test
    public void offerCardShouldReturnEmptySetOnEmptyAlbum() throws Exception {
        AlbumSet albumSet = getFirstAlbumSet(album);
        Card randomCard = getFirstCard(albumSet);

        Set<Event> events = user.offerCard(randomCard.id);

        assertTrue(events.isEmpty());
    }

    @Test
    public void offerCardShouldReturnEmptySetWhenEachAlbumSetMissCard() throws Exception {
        AlbumSet albumSet = getFirstAlbumSet(album);
        AlbumSet secondAlbumSet = album
                .sets.stream()
                .filter(set -> set.id != albumSet.id)
                .findFirst().orElseThrow(RuntimeException::new);
        Card randomCard = getFirstCard(albumSet);
        Card secondSetRandomCard = getFirstCard(secondAlbumSet);

        albumSet.cards.remove(randomCard);

        secondAlbumSet.cards.remove(secondSetRandomCard);

        secondSetRandomCard = getFirstCard(secondAlbumSet);
        secondAlbumSet.cards.remove(secondSetRandomCard);

        Set<Event> events = user.offerCard(secondSetRandomCard.id);

        assertTrue(events.isEmpty());
    }

    @Test
    public void offerCardShouldReturnSetWithTwoDifferentEventsWhenNoLastCardInSet() throws Exception {
        int eventsAmountAfterInsertingLastCard = 2;
        user = new User(userId, album);

        AlbumSet albumSet = getFirstAlbumSet(album);
        Card randomCard = getFirstCard(albumSet);

        albumSet.cards.remove(randomCard);
        Set<Event> events = user.offerCard(randomCard.id);

        assertEquals(eventsAmountAfterInsertingLastCard, events.size());
        assertTrue(events.contains(new Event(user.getId(), Event.Type.SET_FINISHED)));
        assertTrue(events.contains(new Event(user.getId(), Event.Type.ALBUM_FINISHED)));
    }

    @Test
    public void offerCardShouldReturnSetWithSetFinishedEventWhenNoLastCardInTwoSets() throws Exception {
        int eventsAmountAfterInsertingLastForSetCard = 1;
        user = new User(userId, album);

        AlbumSet albumSet = getFirstAlbumSet(album);
        AlbumSet secondAlbumSet = album
                .sets.stream()
                .filter(set -> set.id != albumSet.id)
                .findFirst().orElseThrow(RuntimeException::new);
        Card randomCard = getFirstCard(albumSet);
        Card secondSetRandomCard = getFirstCard(secondAlbumSet);

        albumSet.cards.remove(randomCard);
        secondAlbumSet.cards.remove(secondSetRandomCard);
        Set<Event> events = user.offerCard(randomCard.id);

        assertEquals(eventsAmountAfterInsertingLastForSetCard, events.size());
        assertTrue(events.contains(new Event(user.getId(), Event.Type.SET_FINISHED)));

    }


    private Card getFirstCard(AlbumSet albumSet) {
        return albumSet
                .cards.stream()
                .findFirst().orElseThrow(RuntimeException::new);
    }

    private AlbumSet getFirstAlbumSet(Album album) {
        return album
                .sets.stream()
                .findFirst().orElseThrow(RuntimeException::new);
    }
}