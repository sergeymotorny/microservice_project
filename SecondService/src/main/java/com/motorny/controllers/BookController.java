package com.motorny.controllers;

import com.motorny.dto.BookDto;
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

    @GetMapping("/book/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }

    @PostMapping("/book/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookDto bookDto, @PathVariable("userId") Long userId) {
        return bookService.createBook(bookDto, userId);
    }

    @PutMapping("/book/{id}")
    public BookDto updateBook(@Valid @RequestBody BookDto bookDto,
                              @PathVariable("id") Long id) {
        return bookService.updateBook(bookDto, id);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }
}
