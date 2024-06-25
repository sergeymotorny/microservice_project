package com.motorny.controllers;

import com.motorny.dto.BookDto;
import com.motorny.dto.PopularBookViewDto;
import com.motorny.dto.TitleBookViewDto;
import com.motorny.services.BookService;
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

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBook();
    }

    @PostMapping("/books/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookDto bookDto, @PathVariable("userId") Long userId) {
        return bookService.createBook(bookDto, userId);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/books/user/{userId}")
    public List<TitleBookViewDto> getBooksByUserId(@PathVariable("userId") Long userId) {
        return bookService.getAllBooksByUserId(userId);
    }

    @GetMapping("/books/popular")
    public List<PopularBookViewDto> getPopularBooksForReaders(@RequestParam Integer age,
                                                              @RequestParam Integer limit) {
        return bookService.getPopularBooksByAge(age, limit);
    }
}
