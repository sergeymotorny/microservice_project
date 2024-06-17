package com.motorny.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private Long id;
    private String title;
    private Integer pages;
    private Long userId;
}
