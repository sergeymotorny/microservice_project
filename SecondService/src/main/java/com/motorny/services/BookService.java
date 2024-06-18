package com.motorny.services;

import com.motorny.dto.BookDto;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<BookDto> getAllBook();
    BookDto getBook(Long id);
    BookDto createBook(BookDto bookDto, Long id);
    String deleteBook(Long id);
    List<Map<String, Object>> getAllBooksByUserId(Long id);
    List<Map<String, Object>> getPopularBooksForReadersUnderAge10(Integer age, Integer outputLimit);
}
