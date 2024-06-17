package com.motorny.services.impl;

import com.motorny.dto.BookDto;
import com.motorny.exceptions.UserNotFoundException;
import com.motorny.mappers.BookMapper;
import com.motorny.models.Book;
import com.motorny.repositories.BookRepository;
import com.motorny.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAllBook() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBook(Long id) {
        Book foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Book with " + id + " was not found!"));

        return bookMapper.toBookDto(foundBook);
    }

    @Transactional
    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.toBook(bookDto);
        Book saveBook = bookRepository.save(book);
        return bookMapper.toBookDto(saveBook);
    }

    @Transactional
    @Override
    public BookDto updateBook(BookDto bookDto, Long id) {
        return null;
    }

    @Transactional
    @Override
    public String deleteBook(Long id) {
        Book foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Book with " + id + " was not found!"));

        bookRepository.delete(foundBook);

        return "Book with " + id + " successfully deleted!";
    }
}
