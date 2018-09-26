package com.rosklyar.cards;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import com.rosklyar.cards.domain.User;
import com.rosklyar.cards.service.ConfigurationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlbumCheckerTest {



    private Album album;

    @Before
    public void configure() {

        album = new Album(1L, "Animals", newHashSet(
                new AlbumSet(1L, "Birds", newHashSet(new Card(1L, "Eagle"), new Card(2L, "Cormorant"), new Card(3L, "Sparrow"), new Card(4L, "Raven"))),
                new AlbumSet(2L, "Fish", newHashSet(new Card(5L, "Salmon"), new Card(6L, "Mullet"), new Card(7L, "Bream"), new Card(8L, "Marline")))
        ));
    }

    @Test
    public void checkAlbumSetReturnsTrueIfItFull() {
        User user = new User(0, album);

        final long albumSetId = album.sets.stream()
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .id;
        boolean isSetFull = AlbumChecker.checkIfSetFull(user, albumSetId);

        assertTrue(isSetFull);
    }

    @Test
    public void checkAlbumSetReturnsFalseIfItNotFull() {
        User user = new User(0, album.getEmptyAlbum());

        final long albumSetId = album.sets.stream()
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .id;
        boolean isSetFull = AlbumChecker.checkIfSetFull(user, albumSetId);

        assertFalse(isSetFull);
    }

    @Test
    public void checkAlbumReturnsTrueIfItFull() {
        User user = new User(0, album);

        boolean isAlbumFull = AlbumChecker.checkIfAlbumFull(user);

        assertTrue(isAlbumFull);
    }

    @Test
    public void checkAlbumReturnsFalseIfItNotFull() {
        User user = new User(0, album.getEmptyAlbum());

        boolean isAlbumFull = AlbumChecker.checkIfAlbumFull(user);

        assertFalse(isAlbumFull);
    }
}