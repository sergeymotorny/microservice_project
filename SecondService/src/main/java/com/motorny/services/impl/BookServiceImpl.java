package com.motorny.services.impl;

import com.motorny.dto.BookDto;
import com.motorny.dto.PopularBookViewDto;
import com.motorny.dto.TitleBookViewDto;
import com.motorny.exceptions.BookNotFoundException;
import com.motorny.exceptions.UserNotFoundException;
import com.motorny.mappers.BookMapper;
import com.motorny.models.Book;
import com.motorny.models.User;
import com.motorny.models.projection.PopularBookView;
import com.motorny.models.projection.TitleBookView;
import com.motorny.repositories.BookRepository;
import com.motorny.repositories.UserRepository;
import com.motorny.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAllBook() {

        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDto)
                .toList();
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
    public List<TitleBookViewDto> getAllBooksByUserId(Long id) {
        List<TitleBookView> allBooksByUserId = bookRepository.findAllBooksByUserId(id);

        return allBooksByUserId.stream()
                .map(bookMapper::toTitleBookViewDto)
                .toList();
    }

    @Override
    public List<PopularBookViewDto> getPopularBooksByAge(Integer age, Integer limit) {

        List<PopularBookView> popularBooks =
                bookRepository.mostPopularBooksForReadersUnderAge(age, limit);

        return popularBooks.stream()
                .map(bookMapper::toPopularBookViewDto)
                .toList();
    }
}
