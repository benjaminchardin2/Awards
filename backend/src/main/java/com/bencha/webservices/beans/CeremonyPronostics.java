package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class CeremonyPronostics {
    Map<Long, PronosticChoice> favoritesPicks;
    Map<Long, PronosticChoice> winnerPicks;
}
