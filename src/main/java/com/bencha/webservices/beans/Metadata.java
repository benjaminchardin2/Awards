package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class Metadata {
    private Integer offset;
    private Integer limit;
    private Integer previousOffset;
    private Integer nextOffset;
    private Integer currentPage;
    private Integer pageCount;
    private Integer totalCount;
}
