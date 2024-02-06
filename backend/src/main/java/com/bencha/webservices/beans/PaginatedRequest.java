package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class PaginatedRequest<T> {
    private Integer offset;
    private Integer pageSize;
    private T request;
}
