package com.rosklyar.cards.domain;

import org.junit.Test;


import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.*;
import static org.junit.Assert.*;

public class AlbumSetTest {

    private long id = 0;
    private String albumSetName = "empty";
    private String cardName = "card";


    @Test
    public void returnsFalseOnEmptySet() {
        AlbumSet albumSet = new AlbumSet(id, albumSetName, emptySet());
        Card card = new Card(id, cardName);

        assertFalse(albumSet.containsCard(card.id));
    }

    @Test
    public void returnsTrueAfterCardIsAddedAlready() {
        AlbumSet albumSet = new AlbumSet(id, albumSetName, newHashSet());
        Card card = new Card(id, cardName);
        albumSet.cards.add(card);

        assertTrue(albumSet.containsCard(card.id));
    }

}