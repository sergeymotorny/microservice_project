package com.motorny.mappers;

import com.motorny.dto.BookDto;
import com.motorny.models.Book;
import com.motorny.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class)
public interface BookMapper {

    @Mapping(target = "userId", expression = "java(mapIds(book.getUsers()))")
    BookDto toBookDto(Book book);

    Book toBook(BookDto bookDto);

    default Set<Long> mapIds(Set<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
