package com.motorny.mappers;

import com.motorny.dto.BookDto;
import com.motorny.models.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book toBook(BookDto bookDto);
}
