package com.motorny.services;

import com.motorny.dto.BookDto;
import com.motorny.dto.PopularBookViewDto;
import com.motorny.dto.TitleBookViewDto;
import com.motorny.models.projection.TitleBookView;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBook();
    BookDto createBook(BookDto bookDto, Long id);
    String deleteBook(Long id);
    List<TitleBookViewDto> getAllBooksByUserId(Long id);
}
