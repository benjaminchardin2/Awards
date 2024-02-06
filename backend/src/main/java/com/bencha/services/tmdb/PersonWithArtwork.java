package com.bencha.services.tmdb;

import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.people.PersonPeople;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class PersonWithArtwork {
    private Long personId;
    private Optional<Artwork> artwork;
    private PersonPeople person;
}
