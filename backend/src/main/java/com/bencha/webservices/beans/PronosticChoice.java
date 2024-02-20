package com.bencha.webservices.beans;

import com.bencha.enums.PronosticType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class PronosticChoice {
    private Long participationId;
    private Long nomineeId;
    private Long awardId;
    private Nominee nominee;
    private PronosticType type;
}
