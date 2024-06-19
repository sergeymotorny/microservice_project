package com.motorny.services.impl;

import com.motorny.dto.BookDto;
import com.motorny.exceptions.BookNotFoundException;
import com.motorny.exceptions.UserNotFoundException;
import com.motorny.mappers.BookMapper;
import com.motorny.models.Book;
import com.motorny.models.User;
import com.motorny.models.projection.BookProjection;
import com.motorny.repositories.BookRepository;
import com.motorny.repositories.UserRepository;
import com.motorny.services.BookService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.query.spi.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
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
    public List<Map<String, Object>> getAllBooksByUserId(Long id) {
        List<BookProjection> allBooksByUserId = bookRepository.findAllBooksByUserId(id);

        return allBooksByUserId.stream()
                .map(book -> {
                    Map<String, Object> item = new HashMap<>();

                    item.put("user", book.getFullName());
                    item.put("title", book.getTitle());
                    return item;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getPopularBooksForReadersUnderAge10(Integer age, Integer outputLimit) {
        List<Object[]> booksForReadersUnder10 =
                bookRepository.findTop3MostPopularBooksForReadersUnder10(age, outputLimit);

        return booksForReadersUnder10.stream()
                .map(objects -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("user", objects[0]);
                    item.put("age", objects[1]);
                    item.put("popularity_book", objects[2]);
                    return item;
                })
                .collect(Collectors.toList());
    }
}
