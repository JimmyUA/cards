package com.rosklyar.cards.domain;

import org.junit.Test;


import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.*;
import static org.junit.Assert.*;

public class AlbumSetTest {

    @Test
    public void returnsFalseOnEmptySet() {
        AlbumSet albumSet = new AlbumSet(0l, "empty", emptySet());
        Card card = new Card(0L, "card");

        assertFalse(albumSet.containsCard(card.id));
    }

    @Test
    public void returnsTrueAfterCardIsAddedAlready() {
        AlbumSet albumSet = new AlbumSet(0l, "empty", newHashSet());
        Card card = new Card(0L, "card");
        albumSet.cards.add(card);

        assertTrue(albumSet.containsCard(card.id));
    }

}