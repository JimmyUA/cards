package com.rosklyar.cards.domain;

public class User {
    private long id;
    public Album album;

    public User(long id, Album album) {
        this.id = id;
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public boolean offerCard(Card card, long setId) {
        AlbumSet currentAlbumSet = album.sets.stream()
                .filter(albumSet -> albumSet.id == setId)
                .findFirst().orElseThrow(RuntimeException::new);
        if (currentAlbumSet.containsCard(card.id)){
            return false;
        }
        currentAlbumSet.cards.add(card);
        return true;
    }
}
