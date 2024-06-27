package com.motorny.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDtoFromSecondService {
    private Long id;
    private String fullName;
    private Integer age;
    private Set<BookDto> books;
}
