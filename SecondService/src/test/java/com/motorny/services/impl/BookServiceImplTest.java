package com.motorny.services.impl;

import com.motorny.dto.BookDto;
import com.motorny.mappers.BookMapper;
import com.motorny.models.Book;
import com.motorny.models.User;
import com.motorny.repositories.BookRepository;
import com.motorny.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookMapper bookMapper;

    private Book book;

    private static final Long USER_ID = 15L;
    private static final Long BOOK_ID = 11L;

    @BeforeEach
    void setUp() {
        User user = new User(USER_ID, "Sergey Motorny", 12, new HashSet<>());
        Set<User> userList = new HashSet<>(List.of(user));

        book = Book.builder()
                .id(BOOK_ID)
                .title("Java edition 8")
                .pages(1035)
                .users(userList).build();
    }

    @Test
    void getAllBook() {
        List<Book> mockBooks = List.of(
                new Book(11L, "Programming Patterns", 650, new HashSet<>()),
                new Book(12L, "Algorithms", 800, new HashSet<>())
        );

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<BookDto> result = bookService.getAllBook();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void createBook() {
        BookDto bookDto = new BookDto(
                11L,
                "Programming Patterns",
                650,
                new HashSet<>(List.of(USER_ID)));
        User user = new User(USER_ID, "Sergey Motorny", 12, new HashSet<>());

        book = Book.builder()
                .users(new HashSet<>(List.of(user)))
                .build();

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bookMapper.toBook(bookDto)).thenReturn(book);

        bookService.createBook(bookDto, USER_ID);

        verify(userRepository, times(1)).findById(USER_ID);
        verify(bookRepository, times(1)).save(book);
    }


    @Test
    void deleteBookById() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        bookService.deleteBook(BOOK_ID);

        verify(bookRepository, times(1)).findById(BOOK_ID);

        for (User user : book.getUsers()) {
            verify(userRepository, times(1)).findById(user.getId());
        }

        verify(bookRepository, times(1)).delete(book);
    }
}