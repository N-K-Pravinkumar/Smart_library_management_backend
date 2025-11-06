package com.wecodee.library.management.service;

import com.wecodee.library.management.dto.BookDto;
import com.wecodee.library.management.dto.BorrowDto;
import com.wecodee.library.management.model.Book;
import com.wecodee.library.management.model.BorrowRecord;
import com.wecodee.library.management.repository.BookRepository;
import com.wecodee.library.management.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowRepository borrowRepository;
    public  List<BookDto> getListOfBook() {
        return bookRepository.findByBorrowFalse()
                .stream()
                .filter(book -> !book.isBorrowed())
                .map(this::convertToBookDto).collect(Collectors.toList());
    }

    private BookDto convertToBookDto(Book book) {
        BookDto dto=new BookDto();
        dto.setBookId(book.getBookId());
        dto.setBookName(book.getBookname());
        dto.setAuthor(book.getAuthor());
        dto.setCategory(book.getCategory());
        dto.setBorrowed(book.isBorrowed());
        dto.setReturned(book.isReturned());
        return dto;
    }

    public String borrowBook(long bookId, long studentId) {
        Book book=bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("Book not found with Id :"+bookId));
        if(book.isBorrowed()){
            return "Book is Borrowed by another Student";
        }
        book.setBorrowed(true);
        book.setReturned(false);
        bookRepository.save(book);

        BorrowRecord record=new BorrowRecord();
        record.setStudentId(studentId);
        record.setBorrowDate(LocalDate.now());
        record.setBookId(bookId);
        record.setFine(0);

        return "Book Borrowed Successfully";

    }

    public String returnBook(long bookId, long studentId) {
        Book book=bookRepository.findById(bookId)
                .orElseThrow(()->new RuntimeException("Book not found with id :"+bookId));
        List<BorrowRecord> br=borrowRepository.findByStudentId(studentId);
        BorrowRecord record=br.stream()
                .filter(r-> (r.getBookId()==bookId) && r.getReturnDate()==null)
                .findFirst()
                .orElseThrow(()->new RuntimeException("No active borrow found for this book"));
        book.setReturned(true);
        book.setBorrowed(false);
        bookRepository.save(book);
        record.setReturnDate(LocalDate.now());
        long days= ChronoUnit.DAYS.between(record.getBorrowDate(), record.getReturnDate());
        if (days>14) {
            double fine=(days-14)*2;
            record.setFine(fine);
        }else {
            record.setFine(0);
        }
        borrowRepository.save(record);
        return "Book return Successfully";
    }
    public List<BorrowRecord> getBorrowHistory(Long studentId) {
        return borrowRepository.findByStudentId(studentId);
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRepository.findAll();
    }
}
