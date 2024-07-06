package com.motorny.mappers;

import com.motorny.dto.BookDto;
import com.motorny.models.Book;
import com.motorny.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    private final BookMapper bookMapper = new BookMapperImpl();

    private static Book book;
    private BookDto bookDto;

    @BeforeAll
    static void setUp() {
        book = Book.builder()
                .id(30L)
                .title("Java edition 8")
                .pages(1035)
                .users(new HashSet<>(List.of(
                        User.builder()
                                .id(10L)
                                .fullName("Kirill Shadow")
                                .age(4).build()
                ))).build();
    }

    @Test
    void shouldProperlyMapModelToDto() {
        bookDto = bookMapper.toBookDto(book);

        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getPages(), bookDto.getPages());
        assertFalse(book.getUsers().isEmpty());
    }

    @Test
    void shouldProperlyMapDtoToModel() {
        bookDto = bookMapper.toBookDto(book);

        book = bookMapper.toBook(bookDto);

        assertNotNull(book);
        assertEquals(30L, book.getId());
        assertEquals("Java edition 8", book.getTitle());
        assertEquals(1035, book.getPages());
        assertFalse(bookDto.getUserId().isEmpty());
    }
}