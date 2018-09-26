package com.rosklyar.cards.service;

import com.rosklyar.cards.AlbumChecker;
import com.rosklyar.cards.DefaultConfiguration;
import com.rosklyar.cards.domain.*;

import java.util.Set;
import java.util.function.Consumer;

import static com.google.common.collect.Sets.newHashSet;


/**
 * Created by rostyslavs on 11/21/2015.
 */
public class DefaultCardAssigner implements CardAssigner {

    private Consumer<Event> consumer;
    private Album sampleAlbum;
    private Set<User> users;

    public DefaultCardAssigner() {
        this.users = newHashSet();
        sampleAlbum = DefaultConfiguration.getConfigurationProvider().get();
    }

    @Override
    public void assignCard(long userId, long cardId) {
        User currentUser = users.stream()
                .filter(user -> user.getId() == userId).findFirst()
                .orElse(addUser(userId));

        AlbumSet currentCardSet = sampleAlbum.sets.stream()
                .filter(set -> set.containsCard(cardId)).findFirst()
                .orElseThrow(RuntimeException::new);

        Card currentCard = currentCardSet.cards.stream()
                .filter(card -> card.id == cardId).findFirst()
                .orElseThrow(RuntimeException::new);

        long cardSetId = currentCardSet.id;

        boolean isCardTaken = currentUser.offerCard(currentCard, cardSetId);
        if (isCardTaken){
            boolean isSetFull = AlbumChecker.checkIfSetFull(currentUser, cardSetId);
            boolean isAlbumFull = AlbumChecker.checkIfAlbumFull(currentUser);
            if (isSetFull){
                consumer.accept(new Event(userId, Event.Type.SET_FINISHED));
            }
            if (isAlbumFull){
                consumer.accept(new Event(userId, Event.Type.ALBUM_FINISHED));
            }
        }
    }

    @Override
    public void subscribe(Consumer<Event> consumer) {
        this.consumer = consumer;
    }

    private User addUser(long userId){
        Album emptySetsAlbum = getEmptySetsAlbum(sampleAlbum);
        final User user = new User(userId, emptySetsAlbum);
        users.add(user);
        return user;
    }

    private Album getEmptySetsAlbum(Album album) {
        return album.getEmptyAlbum();
    }

}
