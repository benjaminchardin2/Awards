package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class AdditionalResults {
    private List<SearchResult> searchResults;
    private Boolean hasMoreResults;
    private Integer nextPage;
}
