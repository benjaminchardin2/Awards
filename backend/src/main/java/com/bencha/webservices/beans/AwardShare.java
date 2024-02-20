package com.bencha.webservices.beans;

import com.bencha.enums.AwardsType;
import com.bencha.enums.PronosticType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class AwardShare {
    private String awardTitle;
    private AwardsType awardType;
    private PronosticType pronosticType;
    private Nominee nominee;
}
