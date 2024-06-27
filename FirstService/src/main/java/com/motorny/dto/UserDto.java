package com.motorny.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String fullName;
    private Integer age;
    private Set<Long> bookIds;
}
