package com.motorny.controllers;

import com.motorny.dto.BookDto;
import com.motorny.services.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/book/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/book/user/{userId}")
    public List<Map<String, Object>> getBooksByUserId(@PathVariable("userId") Long userId) {
        return bookService.getAllBooksByUserId(userId);
    }

    @GetMapping("/book")
    public List<Map<String, Object>> getPopularBooksForReaders(@RequestParam Integer age,
                                                               @RequestParam Integer limit) {
        return bookService.getPopularBooksForReadersUnderAge10(age, limit);
    }
}
