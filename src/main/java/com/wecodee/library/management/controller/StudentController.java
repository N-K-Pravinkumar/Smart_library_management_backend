package com.wecodee.library.management.controller;

import com.wecodee.library.management.dto.BorrowRecordDTO;
import com.wecodee.library.management.dto.StudentDashboardDTO;
import com.wecodee.library.management.dto.StudentHomeDTO;
import com.wecodee.library.management.dto.UserdashboardDTO;
import com.wecodee.library.management.model.Book;
import com.wecodee.library.management.model.BorrowRecord;
import com.wecodee.library.management.model.User;
import com.wecodee.library.management.repository.BookRepository;
import com.wecodee.library.management.repository.BorrowRepository;
import com.wecodee.library.management.repository.UserRepository;
import com.wecodee.library.management.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;



@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private  final StudentService studentService;

    public StudentController(BorrowRepository borrowRepository, BorrowRepository borrowRepository1,
                             BookRepository bookRepository,
                             UserRepository userRepository, StudentService studentService) {
        this.borrowRepository = borrowRepository1;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.studentService = studentService;
    }


    @GetMapping("/home/{studentId}")
    public StudentHomeDTO getStudentHome(@PathVariable long studentId) {
        return studentService.getStudentHomeSummary(studentId);
    }
//    @PostMapping("/book/borrow")
//    @Transactional
//    public ResponseEntity<?> borrowBook(@RequestParam Long bookId, @RequestParam Long studentId) {
//        Optional<Book> optionalBook = bookRepository.findById(bookId);
//        Optional<User> optionalStudent = userRepository.findById(studentId);
//
//        if (optionalBook.isEmpty() || optionalStudent.isEmpty()) {
//            return ResponseEntity.badRequest().body("Invalid bookId or studentId");
//        }
//
//        Book book = optionalBook.get();
//        User student = optionalStudent.get();
//
//        if (book.isBorrowed()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book is already borrowed");
//        }
//
//        long borrowedCount = borrowRecordRepository.countByUser_UserIdAndReturnDateIsNull(studentId);
//        if (borrowedCount >= 3) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("You cannot borrow more than 3 books at a time.");
//        }
//
//        book.setBorrowed(true);
//        bookRepository.save(book);
//
//        BorrowRecord borrowRecord = new BorrowRecord();
//        borrowRecord.setBook(book);
//        borrowRecord.setUser(student);
//        borrowRecord.setBorrowDate(LocalDate.now());
//        borrowRecord.setReturnDate(null);
//        borrowRecord.setOverDueDays(0);
//        borrowRecord.setFine(0);
//        borrowRecord.setPay(false);
//        borrowRecordRepository.save(borrowRecord);
//
//        return ResponseEntity.ok(borrowRecord);
//    }

    @PutMapping("/transaction/{borrowId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long borrowId) {
        BorrowRecord borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        LocalDate today = LocalDate.now();
        int overdueDays = 0;
        double fineAmount = 0;
        if (borrow.getBorrowDate().plusDays(2).isBefore(today)) {
            overdueDays = (int) java.time.temporal.ChronoUnit.DAYS.between(
                    borrow.getBorrowDate().plusDays(2), today);
            fineAmount = overdueDays * 10.0;
        }

        borrow.setOverDueDays(overdueDays);
        borrow.setFine(fineAmount);

        if (fineAmount > 0 && !borrow.isPay()) {
            return ResponseEntity.ok(Map.of(
                    "message", "You need to pay fine before returning",
                    "fine", fineAmount,
                    "borrowId", borrow.getBorrowId()
            ));
        }

        borrow.setReturnDate(today);
        borrow.setPay(true);
        borrowRepository.save(borrow);

        Book book = borrow.getBook();
        book.setBorrowed(false);
        book.setReturned(true);
        bookRepository.save(book);

        return ResponseEntity.ok(Map.of(
                "message", "Book returned successfully",
                "borrowed", book.isBorrowed(),
                "returned", book.isReturned()
        ));
    }
    @GetMapping("/transaction/{studentId}")
    public ResponseEntity<List<BorrowRecord>> getStudentTransactions(@PathVariable Long studentId) {
        List<BorrowRecord> transactions = borrowRepository.findByUser_UserId(studentId);
        return ResponseEntity.ok(transactions);
    }
    @PutMapping("/transaction/{borrowId}/pay")
    public ResponseEntity<?> payFine(@PathVariable Long borrowId) {
        BorrowRecord borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        borrow.setPay(true);
        borrowRepository.save(borrow);

        return ResponseEntity.ok(Map.of(
                "message", "Fine paid successfully",
                "borrowId", borrow.getBorrowId()
        ));
    }
}
