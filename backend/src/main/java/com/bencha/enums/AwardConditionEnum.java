package com.bencha.enums;

import com.google.common.base.Strings;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCredit;
import info.movito.themoviedbapi.model.people.PersonCrew;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Getter
@AllArgsConstructor
public enum AwardConditionEnum {
    RELEASE_DATE_BETWEEN("RELEASE_DATE_BETWEEN",
        null,
        null,
        (
            (personCredit, s) -> {
                if (!Strings.isNullOrEmpty(personCredit.getReleaseDate())) {
                    LocalDate realeaseDate = LocalDate.parse(personCredit.getReleaseDate(), DateTimeFormatter.ISO_DATE);
                    int debutYear = Integer.parseInt(s.split(",")[0]);
                    int endYear = Integer.parseInt(s.split(",")[1]);
                    return (debutYear <= realeaseDate.getYear() && realeaseDate.getYear() <= endYear);
                } return false;
            }
        )
    ),
    DEPARTMENT_IN("DEPARTMENT_IN",
        (
            (personCrew, s) -> Arrays.stream(s.split(",")).toList().contains(personCrew.getDepartment())
        ),
        null,
        (
            (personCredit, s) -> Arrays.stream(s.split(",")).toList().contains(personCredit.getDepartment())
        )
    ),
    ;

    private final String name;
    private final BiFunction<PersonCrew, String, Boolean> personCrewMatcher;
    private final BiFunction<PersonCast, String, Boolean> personCastMatcher;
    private final BiFunction<PersonCredit, String, Boolean> personCreditMatcher;

    public static Optional<AwardConditionEnum> findByConditionName(String conditionName) {
        return Arrays.stream(values())
            .filter(awardConditionEnum -> awardConditionEnum.getName().equals(conditionName))
            .findFirst();
    }

    public static List<String> personCreditMatchersName() {
        return Arrays.stream(values())
            .filter(awardConditionEnum -> awardConditionEnum.personCreditMatcher != null)
            .map(AwardConditionEnum::getName)
            .toList();
    }

    public static List<String> personCastMatchersName() {
        return Arrays.stream(values())
            .filter(awardConditionEnum -> awardConditionEnum.personCastMatcher != null)
            .map(AwardConditionEnum::getName)
            .toList();
    }

    public static List<String> personCrewMatchersName() {
        return Arrays.stream(values())
            .filter(awardConditionEnum -> awardConditionEnum.personCrewMatcher != null)
            .map(AwardConditionEnum::getName)
            .toList();
    }
}
