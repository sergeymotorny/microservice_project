package com.motorny.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BookDto {

    private Long id;
    private String title;
    private Integer pages;
    private Set<UserDto> users;
}
