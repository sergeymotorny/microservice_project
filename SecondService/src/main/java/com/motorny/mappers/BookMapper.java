package com.motorny.mappers;

import com.motorny.dto.BookDto;
import com.motorny.dto.PopularBookViewDto;
import com.motorny.dto.TitleBookViewDto;
import com.motorny.models.Book;
import com.motorny.models.User;
import com.motorny.models.projection.PopularBookView;
import com.motorny.models.projection.TitleBookView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class, imports = java.util.HashSet.class)
public interface BookMapper {

    @Mapping(target = "userId", expression = "java(mapUserToUserId(book.getUsers()))")
    BookDto toBookDto(Book book);

    @Mapping(target = "users", expression = "java(new HashSet<>())")
    Book toBook(BookDto bookDto);

    PopularBookViewDto toPopularBookViewDto(PopularBookView popularBookView);

    PopularBookView toPopularBookView(PopularBookViewDto popularBookViewDto);

    TitleBookViewDto toTitleBookViewDto(TitleBookView titleBookView);

    TitleBookView toTitleBookView(TitleBookViewDto toTitleBookViewDto);

    default Set<Long> mapUserToUserId(Set<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
