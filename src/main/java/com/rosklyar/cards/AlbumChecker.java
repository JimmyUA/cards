package com.rosklyar.cards;

import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.User;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class AlbumChecker {

    public static boolean checkIfSetFull(User currentUser, long albumSetId) {
        AlbumSet userSet = currentUser.album.sets
                .stream().
                        filter(albumSet -> albumSet.id == albumSetId)
                .findFirst().orElseThrow(RuntimeException::new);

        AlbumSet sampleSet = DefaultConfiguration.getConfigurationProvider().get().sets.stream().
                filter(albumSet -> albumSet.id == albumSetId)
                .findFirst().orElseThrow(RuntimeException::new);

        return userSet.cards.size() == sampleSet.cards.size();
    }


    public static boolean checkIfAlbumFull(User user) {
        int cardsAmountSample = DefaultConfiguration.getConfigurationProvider().get().sets.
                stream().map(set -> set.cards).flatMap(Collection::stream).collect(toList()).size();

        int cardsAmountUser = user.album.sets.
                stream().map(set -> set.cards).flatMap(Collection::stream).collect(toList()).size();
        return cardsAmountSample == cardsAmountUser;
    }

}

