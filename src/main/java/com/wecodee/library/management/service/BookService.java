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

    public BookDto saveBook(BookDto dto) {
        Book book = new Book();
        book.setBookName(dto.getBookName());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setBorrowed(dto.isBorrowed());
        book.setReturned(dto.isReturned());
        book.setAvailableCopies(dto.getAvailableCopies());
        Book saved = bookRepository.save(book);
        return convertToDto(saved);
    }

    public List<BookDto> searchBooks(Long bookId, String bookName, String author) {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .filter(b -> bookId == null || b.getBookId().equals(bookId))
                .filter(b -> bookName == null || b.getBookName().toLowerCase().contains(bookName.toLowerCase()))
                .filter(b -> author == null || b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    public List<BookDto> getBooksByBorrowedStatus(boolean borrowed) {
        return bookRepository.findAll().stream()
                .filter(b -> b.isBorrowed() == borrowed)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BookDto convertToDto(Book b) {
        return new BookDto(b.getBookId(), b.getBookName(), b.getAuthor(), b.getCategory(),b.getAvailableCopies(),b.isBorrowed(), b.isReturned());
    }

}
