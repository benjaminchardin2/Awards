package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class Nominee {
    private Long nomineeId;
    private String movieTitle;
    private String frenchMovieTitle;
    private String movieMediaUrl;
    private String personMediaUrl;
    private String personName;
    private Integer tmdbMovieId;
    private Integer tmdbPersonId;
}
