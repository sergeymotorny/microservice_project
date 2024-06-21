package com.motorny.services.impl;

import com.motorny.dto.BookDto;
import com.motorny.dto.BookProjectionDto;
import com.motorny.exceptions.BookNotFoundException;
import com.motorny.exceptions.UserNotFoundException;
import com.motorny.mappers.BookMapper;
import com.motorny.models.Book;
import com.motorny.models.User;
import com.motorny.models.projection.BookProjection;
import com.motorny.repositories.BookRepository;
import com.motorny.repositories.CustomBookRepository;
import com.motorny.repositories.UserRepository;
import com.motorny.services.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService, CustomBookRepository {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;

    @PersistenceContext
    private EntityManager entityManager;

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
                .orElseThrow(() -> new BookNotFoundException("Book with " + id + " was not found!"));

        return bookMapper.toBookDto(foundBook);
    }

    @Transactional
    @Override
    public BookDto createBook(BookDto bookDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Unable to get user for book"));

        Book book = bookMapper.toBook(bookDto);

        book.addUser(user);
        user.addBook(book);

        Book saveBook = bookRepository.save(book);

        return bookMapper.toBookDto(saveBook);
    }

    @Transactional
    @Override
    public String deleteBook(Long id) {
        Book foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with " + id + " was not found!"));

        bookRepository.delete(foundBook);

        return "Book with " + id + " successfully deleted!";
    }


    @Override
    public List<BookProjectionDto> getAllBooksByUserId(Long id) {
        List<BookProjection> allBooksByUserId = bookRepository.findAllBooksByUserId(id);

        return allBooksByUserId.stream()
                .map(bookMapper::toBookProjectionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookProjectionDto> findMostPopularBooksForReadersUnderAge(Integer age, Integer outputLimit) {

        @SuppressWarnings("unchecked")
        List<BookProjection> resultList = entityManager
                .createNamedQuery("BookProjection.findBooksForUserUnderAge")
                .setParameter(1, age)
                .setParameter(2, outputLimit)
                .getResultList();

        return resultList.stream()
                .map(bookMapper::toBookProjectionDto)
                .collect(Collectors.toList());
    }
}
