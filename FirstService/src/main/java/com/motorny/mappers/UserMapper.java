package com.motorny.mappers;

import com.motorny.dto.BookDto;
import com.motorny.dto.UserDto;
import com.motorny.dto.UserDtoFromSecondService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "books", target = "bookIds")
    UserDto toUserDto(UserDtoFromSecondService userDtoFromSecondService);

    default Set<Long> mapToBookId(Set<BookDto> books) {
        return books.stream()
                .map(BookDto::getId)
                .collect(Collectors.toSet());
    }
}
