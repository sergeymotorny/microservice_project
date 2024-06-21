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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class)
public interface BookMapper {

    @Mapping(target = "userId", expression = "java(mapUserToUserId(book.getUsers()))")
    BookDto toBookDto(Book book);

    @Mapping(target = "users", source = "userId")
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

    default Set<User> mapUserIdToUser(Set<Long> ids) {
        if (ids == null) {
            return new HashSet<>();
        }

        return ids.stream()
                .map(id -> {
                    User user = new User();
                    user.setId(id);
                    return user;
                })
                .collect(Collectors.toSet());
    }
}
