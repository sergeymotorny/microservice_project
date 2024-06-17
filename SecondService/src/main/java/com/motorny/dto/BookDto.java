package com.motorny.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BookDto {

    private Long id;
    private String title;
    private Integer pages;
    private Set<Long> userId;
}
