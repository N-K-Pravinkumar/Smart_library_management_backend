package com.wecodee.library.management.service;

import com.wecodee.library.management.dto.BookDto;
import com.wecodee.library.management.model.Book;
import com.wecodee.library.management.model.BorrowRecord;
import com.wecodee.library.management.model.User;
import com.wecodee.library.management.repository.BookRepository;
import com.wecodee.library.management.repository.BorrowRepository;
import com.wecodee.library.management.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private AuthRepository authRepository;

    public List<BookDto> getListOfBook() {
        return bookRepository.findAll()
                .stream()
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

    public String borrowBook(long bookId, long studentId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with Id: " + bookId));
        if (book.isBorrowed()) {
            return "Book is already borrowed by another student.";
        }

        User student = authRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with Id: " + studentId));

        BorrowRecord record = new BorrowRecord();
        record.setStudent(student);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setFine(0);

        book.setBorrowed(true);
        book.setReturned(false);
        bookRepository.save(book);
        borrowRepository.save(record);

        return "Book borrowed successfully.";
    }

    public String returnBook(long bookId, long studentId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with Id: " + bookId));

        BorrowRecord record = borrowRepository
                .findByBook_BookIdAndUserIdAndReturnDateIsNull(bookId, studentId)
                .orElseThrow(() -> new RuntimeException("No active borrow found for this book."));

        record.setReturnDate(LocalDate.now());

        long days = ChronoUnit.DAYS.between(record.getBorrowDate(), record.getReturnDate());
        if (days > 10) {
            record.setFine((days - 10) * 10);
        } else {
            record.setFine(0);
        }

        // Update book
        book.setBorrowed(false);
        book.setReturned(true);
        bookRepository.save(book);
        borrowRepository.save(record);

        return "Book returned successfully.";
    }

    public List<BorrowRecord> getBorrowHistory(Long studentId) {
        return borrowRepository.findByUserId(studentId);
    }
}
