package com.motorny.services;

import com.motorny.dto.BookDto;
import com.motorny.dto.BookProjectionDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBook();
    BookDto getBook(Long id);
    BookDto createBook(BookDto bookDto, Long id);
    String deleteBook(Long id);
    List<BookProjectionDto> getAllBooksByUserId(Long id);
    //List<BookProjectionDto> getPopularBooksForReadersUnderAge(String fullName, String title, Integer age);
}
