package com.motorny.controllers;

import com.motorny.dto.BookDto;
import com.motorny.dto.BookProjectionDto;
import com.motorny.services.BookService;
import com.motorny.services.impl.BookServiceImpl;
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
    private final BookServiceImpl bookServiceImpl;

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBook();
    }

    @GetMapping("/book/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return bookService.getBook(id);
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
    public List<BookProjectionDto> getBooksByUserId(@PathVariable("userId") Long userId) {
        return bookService.getAllBooksByUserId(userId);
    }

    @GetMapping("/book/populars")
    public List<BookProjectionDto> getPopularBooksForReaders(@RequestParam Integer age,
                                                             @RequestParam Integer limit) {
        return bookServiceImpl.findMostPopularBooksForReadersUnderAge(age, limit);
    }
}
