package com.motorny.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TitleBookViewDto {

    private String fullName;
    private String title;
}
