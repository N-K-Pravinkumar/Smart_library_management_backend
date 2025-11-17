package com.wecodee.library.management.controller;

import com.wecodee.library.management.dto.BookDto;
import com.wecodee.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getBooks(
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String author
    ) {
        return bookService.searchBooks(bookId, bookName, author);
    }

    @GetMapping("/borrowed")
    public List<BookDto> getBorrowedBooks() {
        return bookService.getBooksByBorrowedStatus(true);
    }


    @GetMapping("/not-borrowed")
    public List<BookDto> getAvailableBooks() {
        return bookService.getBooksByBorrowedStatus(false);
    }

    @GetMapping("/{bookId}")
    public BookDto getBookById(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }
}
