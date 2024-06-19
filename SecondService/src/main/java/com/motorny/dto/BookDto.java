package com.motorny.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "Book title is missing")
    private String title;

    @Size(min = 1, max = 5000, message = "Invalid value for book page count")
    private Integer pages;

    private Set<Long> userId;
}
