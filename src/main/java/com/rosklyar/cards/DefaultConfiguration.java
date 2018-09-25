package com.rosklyar.cards;

import com.rosklyar.cards.domain.Album;
import com.rosklyar.cards.domain.AlbumSet;
import com.rosklyar.cards.domain.Card;
import com.rosklyar.cards.service.ConfigurationProvider;

import static com.google.common.collect.Sets.newHashSet;

public final class DefaultConfiguration implements Configuration{

    private static final Album album = new Album(1L, "Animals",newHashSet(
            new AlbumSet(1L, "Birds",newHashSet(new Card(1L, "Eagle"), new Card(2L, "Cormorant"), new Card(3L, "Sparrow"), new Card(4L, "Raven"))),
            new AlbumSet(2L, "Fish", newHashSet(new Card(5L, "Salmon"), new Card(6L, "Mullet"), new Card(7L, "Bream"), new Card(8L, "Marline")))
            ));



    public static ConfigurationProvider getConfigurationProvider(){
        return DefaultConfigurationProvider.INSTANCE;
    }


    private static class DefaultConfigurationProvider implements ConfigurationProvider{

        public static final DefaultConfigurationProvider INSTANCE = new DefaultConfigurationProvider();

        @Override
        public Album get() {
            return album;
        }

        private DefaultConfigurationProvider(){}
    }
}
