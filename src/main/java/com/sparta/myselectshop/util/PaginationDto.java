package com.sparta.myselectshop.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {
    private int page;
    private int size;
    private String sortBy;
    private Boolean isAsc;
}
