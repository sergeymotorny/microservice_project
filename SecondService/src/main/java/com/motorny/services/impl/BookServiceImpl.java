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
    public BookDto createBook(BookDto bookDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Unable to get user id '" + userId + "' for book"));

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
                .orElseThrow(() -> new BookNotFoundException("Book with id '" + id + "' was not found!"));

        foundBook.removeUsers();

        bookRepository.delete(foundBook);

        return "Book with " + id + " successfully deleted!";
    }


    @Override
    public List<TitleBookViewDto> getAllBooksByUserId(Long id) {

        List<TitleBookView> allBooksByUserId = bookRepository.findAllBooksByUserId(id);

        if (allBooksByUserId.isEmpty()) {
            throw new UserNotFoundException("User with id '" + id + "' was not found!");
        }

        return allBooksByUserId.stream()
                .map(bookMapper::toTitleBookViewDto)
                .toList();
    }

    @Override
    public List<PopularBookViewDto> getPopularBooksByAge(Integer age, Integer limit) {

        List<PopularBookView> popularBooks =
                bookRepository.mostPopularBooksForReadersUnderAge(age, limit);

        if (popularBooks.isEmpty()) {
            throw new BookNotFoundException("The list of books is empty, please enter the correct age and limit!");
        }

        return popularBooks.stream()
                .map(bookMapper::toPopularBookViewDto)
                .toList();
    }
}
