package com.bencha.webservices.beans;

import com.bencha.enums.AwardsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class NomineeRequest {
    private Long movieId;
    private Long personId;
    private AwardsType awardsType;
}
