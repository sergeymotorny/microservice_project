package com.motorny.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookProjectionDto {

    private Long id;
    private String fullName;
    private String title;
    private String age;

    public BookProjectionDto(String fullName, String title) {
        this.fullName = fullName;
        this.title = title;
    }

    public BookProjectionDto(Long id, String fullName, String title, String age) {
        this.id = id;
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }
}
