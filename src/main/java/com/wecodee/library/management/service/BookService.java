package com.wecodee.library.management.service;

import com.wecodee.library.management.dto.BookDto;
import com.wecodee.library.management.model.Book;
import com.wecodee.library.management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDto> searchBooks(Long bookId, String bookName, String author) {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .filter(book -> bookId == null || book.getBookId()==(bookId))
                .filter(book -> bookName == null ||
                        book.getBookname().toLowerCase().contains(bookName.toLowerCase()))
                .filter(book -> author == null ||
                        book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .map(this::convertToBookDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(this::convertToBookDto)
                .orElse(null);
    }
    public List<BookDto> getBooksByBorrowedStatus(boolean borrowed) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .filter(book -> book.isBorrowed() == borrowed)
                .map(this::convertToBookDto)
                .collect(Collectors.toList());
    }

    private BookDto convertToBookDto(Book book) {
        BookDto dto = new BookDto();
        dto.setBookId(book.getBookId());
        dto.setBookName(book.getBookname());
        dto.setAuthor(book.getAuthor());
        dto.setCategory(book.getCategory());
        dto.setBorrowed(book.isBorrowed());
        dto.setReturned(book.isReturned());
        return dto;
    }
}
