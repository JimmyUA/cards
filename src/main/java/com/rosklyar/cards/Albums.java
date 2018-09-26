package com.rosklyar.cards;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import com.rosklyar.cards.domain.User;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class Albums {

    public static boolean checkIfSetFull(User currentUser, long albumSetId) {
        AlbumSet userSet = currentUser.getAlbum().sets
                .stream().filter(albumSet -> albumSet.id == albumSetId)
                .findFirst().orElseThrow(RuntimeException::new);

        AlbumSet sampleSet = DefaultConfiguration.getConfigurationProvider().get().sets
                .stream().filter(albumSet -> albumSet.id == albumSetId)
                .findFirst().orElseThrow(RuntimeException::new);

        return setsSizeAreEqual(userSet, sampleSet);
    }

    private static boolean setsSizeAreEqual(AlbumSet userSet, AlbumSet sampleSet) {
        return userSet.cards.size() == sampleSet.cards.size();
    }


    public static boolean checkIfAlbumFull(User user) {
        int cardsAmountSample = DefaultConfiguration.getConfigurationProvider().get().sets
                .stream().map(set -> set.cards)
                .flatMap(Collection::stream).collect(toList()).size();

        int cardsAmountUser = user.getAlbum().sets
                .stream().map(set -> set.cards)
                .flatMap(Collection::stream).collect(toList()).size();

        return cardsAmountSample == cardsAmountUser;
    }


    public static AlbumSet findAlbumSetByCardId(long cardId, Album album) {
        return album.sets.stream()
                .filter(set -> set.containsCard(cardId)).findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public static AlbumSet findSetById(long cardSetId, Album album) {
        return album.sets.stream()
                .filter(albumSet -> albumSet.id == cardSetId)
                .findFirst().orElseThrow(RuntimeException::new);
    }

    public static Card findCardById(long cardId, AlbumSet sampleAlbumSet) {
        return sampleAlbumSet.cards.stream()
                .filter(card -> card.id == cardId).findFirst()
                .orElseThrow(RuntimeException::new);
    }
}

