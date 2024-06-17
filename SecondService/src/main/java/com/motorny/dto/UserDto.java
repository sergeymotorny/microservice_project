package com.motorny.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {

    private Long id;
    private String fullName;
    private Integer age;
    private Set<BookDto> books;
}
