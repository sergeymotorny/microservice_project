package com.motorny.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookProjectionDto {

    private String fullName;
    private String title;
    private String age;
}
