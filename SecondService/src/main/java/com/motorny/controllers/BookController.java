package com.motorny.controllers;

import com.motorny.dto.BookDto;
import com.motorny.dto.PopularBookViewDto;
import com.motorny.dto.TitleBookViewDto;
import com.motorny.services.BookService;
import com.motorny.services.impl.CustomBookRepositoryImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;
    private final CustomBookRepositoryImpl customBookRepository;

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBook();
    }

    @PostMapping("/book/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookDto bookDto, @PathVariable("userId") Long userId) {
        return bookService.createBook(bookDto, userId);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/book/user/{userId}")
    public List<TitleBookViewDto> getBooksByUserId(@PathVariable("userId") Long userId) {
        return bookService.getAllBooksByUserId(userId);
    }

    @GetMapping("/popular/books")
    public List<PopularBookViewDto> getPopularBooksForReaders(@RequestParam Integer age,
                                                              @RequestParam Integer limit) {
        return customBookRepository.findMostPopularBooksForReadersUnderAge(age, limit);
    }
}
