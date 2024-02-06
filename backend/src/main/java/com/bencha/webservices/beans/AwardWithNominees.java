package com.bencha.webservices.beans;

import com.bencha.enums.AwardsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class AwardWithNominees {
    private Long awardId;
    private String awardName;
    private AwardsType type;
    private List<Nominee> nominees;
}
