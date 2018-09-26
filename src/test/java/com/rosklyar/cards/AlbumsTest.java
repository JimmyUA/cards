package com.rosklyar.cards;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import com.rosklyar.cards.domain.User;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static com.rosklyar.cards.Albums.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AlbumsTest {


    private User fullAlbumUser;
    private User emptyAlbumUser;
    private Album album;
    private AlbumSet firstAlbumSet;
    private Card firstCard;
    private long randomAlbumSetId = 1;
    private int userId = 0;
    private long wrongId = 10L;


    @Before
    public void configure() {

        firstCard = new Card(1L, "Eagle");
        firstAlbumSet = new AlbumSet(1L, "Birds",
                newHashSet(firstCard, new Card(2L, "Cormorant"), new Card(3L, "Sparrow"), new Card(4L, "Raven")));

        AlbumSet secondAlbumSet = new AlbumSet(2L, "Fish",
                newHashSet(new Card(5L, "Salmon"), new Card(6L, "Mullet"), new Card(7L, "Bream"), new Card(8L, "Marline")));

        album = new Album(1L, "Animals", newHashSet(firstAlbumSet, secondAlbumSet));

        fullAlbumUser = getFullAlbumUser();
        emptyAlbumUser = getEmptyAlbumUser();
    }

    @Test
    public void checkAlbumSetReturnsTrueIfItFull() {
        assertTrue(checkIfSetFull(fullAlbumUser, randomAlbumSetId));
    }

    @Test
    public void checkAlbumSetReturnsFalseIfItNotFull() {
        assertFalse(checkIfSetFull(emptyAlbumUser, randomAlbumSetId));
    }

    @Test
    public void checkAlbumReturnsTrueIfItFull() {
        assertTrue(checkIfAlbumFull(fullAlbumUser));
    }

    @Test
    public void checkAlbumReturnsFalseIfItNotFull() {
        assertFalse(checkIfAlbumFull(emptyAlbumUser));
    }

    @Test(expected = RuntimeException.class)
    public void findAlbumSetByCardIdShouldThrowExceptionOnWrongId() throws Exception {
        findAlbumSetByCardId(wrongId, album);
    }

    @Test(expected = RuntimeException.class)
    public void findAlbumSetByIdShouldThrowExceptionOnWrongId() throws Exception {
        findSetById(wrongId, album);
    }

    @Test(expected = RuntimeException.class)
    public void findCardByIdShouldThrowExceptionOnWrongId() throws Exception {
        findCardById(wrongId, firstAlbumSet);
    }

    @Test
    public void shouldFindCorrectSetByCardId() throws Exception {

        assertEquals(firstAlbumSet, findAlbumSetByCardId(firstCard.id, album));
    }

    @Test
    public void shouldFindCorrectSetById()throws Exception {
        assertEquals(firstAlbumSet, findSetById(firstAlbumSet.id, album));
    }

    @Test
    public void shouldFindCorrectCardById() throws Exception {
        assertEquals(firstCard, findCardById(firstCard.id, firstAlbumSet));
    }

    private User getEmptyAlbumUser() {
        return new User(userId, album.getEmptyAlbum());
    }

    private User getFullAlbumUser() {
        return new User(userId, album);
    }
}