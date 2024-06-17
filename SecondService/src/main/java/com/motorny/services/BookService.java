package com.motorny.services;

import com.motorny.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBook();
    BookDto getBook(Long id);
    BookDto createBook(BookDto bookDto);
    BookDto updateBook(BookDto bookDto, Long id);
    String deleteBook(Long id);
}
