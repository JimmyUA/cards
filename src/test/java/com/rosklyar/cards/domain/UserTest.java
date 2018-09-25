package com.rosklyar.cards.domain;

import com.rosklyar.cards.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private int userId = 1;
    private User user;
    private Album album;

    @Before
    public void setUp() throws Exception {
        album = DefaultConfiguration.getConfigurationProvider().get();
        Album emptyAlbum = album.getEmptyAlbum();
        user = new User(userId, emptyAlbum);
    }

    @Test
    public void offerCardShouldReturnTrueOnEmptyAlbum() throws Exception {
        AlbumSet albumSet = getFirstAlbumSet(album);
        Card randomCard = getFirstCard(albumSet);

        assertTrue(user.offerCard(randomCard, albumSet.id));
    }

    @Test
    public void offerCardShouldReturnFalseIfCardInSet() throws Exception {
        AlbumSet albumSet = getFirstAlbumSet(album);
        Card randomCard = getFirstCard(albumSet);

        user.offerCard(randomCard, albumSet.id);

        assertFalse(user.offerCard(randomCard, albumSet.id));
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