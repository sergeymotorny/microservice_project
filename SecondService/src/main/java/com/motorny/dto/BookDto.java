package com.motorny.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "Book title is missing")
    private String title;

    @Min(value = 1, message = "The number of pages cannot be lower than 1")
    @Max(value = 5000, message = "The number of pages cannot be more than 5000")
    private Integer pages;

    private Set<Long> userId;
}
