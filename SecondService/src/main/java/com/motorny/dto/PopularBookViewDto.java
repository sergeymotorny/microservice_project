package com.motorny.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PopularBookViewDto {

    private String fullName;
    private String title;
    private Integer age;
}
