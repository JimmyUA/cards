package com.rosklyar.cards.service;

import com.rosklyar.cards.DefaultConfiguration;
import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.Event;
import com.rosklyar.cards.domain.User;

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

        Set<Event> events = currentUser.offerCard(cardId);

        events.forEach(event -> consumer.accept(event));
    }

    @Override
    public void subscribe(Consumer<Event> consumer) {
        this.consumer = consumer;
    }

    private User addUser(long userId) {
        Album emptySetsAlbum = getEmptySetsAlbum(sampleAlbum);
        final User user = new User(userId, emptySetsAlbum);
        users.add(user);
        return user;
    }

    private Album getEmptySetsAlbum(Album album) {
        return album.getEmptyAlbum();
    }

}
