package com.motorny.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "The person's full name cannot be empty")
    private String fullName;

    @Size(min = 1, max = 100, message = "Age greater than 100 or less than 1 year")
    private Integer age;

    private Set<BookDto> books;
}
