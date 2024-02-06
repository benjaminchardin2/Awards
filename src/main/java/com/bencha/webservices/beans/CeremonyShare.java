package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Setter
@Getter
public class CeremonyShare {
    private String ceremonyTitle;
    private Long ceremonyId;
    private List<AwardShare> awardShares;
}
