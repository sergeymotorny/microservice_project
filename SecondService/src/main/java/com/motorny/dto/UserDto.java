package com.motorny.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class UserDto implements Serializable {

    private Long id;

    @NotBlank(message = "The person's full name cannot be empty")
    private String fullName;

    @Min(value = 1, message = "Age cannot be less than 1 year")
    @Max(value = 112, message = "Age cannot be more than 112")
    private Integer age;

    private Set<BookDto> books;
}
