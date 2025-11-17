package com.wecodee.library.management.controller;

import com.wecodee.library.management.dto.*;
import com.wecodee.library.management.model.BorrowRecord;
import com.wecodee.library.management.repository.BorrowRepository;
import com.wecodee.library.management.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/librarian")
public class LibrarianController {

    public LibBookService libBookService;
    private final DashboardService dashboardService;
    private final BorrowRepository borrowRepository;
    private final BorrowRecordService borrowRecordService;


    public BookService bookService;
    public LibrarianController(LibBookService LibBookService, DashboardService dashboardService, BorrowRepository borrowRepository, BorrowRecordService borrowRecordService) {
        this.libBookService = LibBookService;
        this.dashboardService = dashboardService;
        this.borrowRepository = borrowRepository;
        this.borrowRecordService = borrowRecordService;
    }

    @GetMapping("/home")
    public DashboardSummaryDTO getSummary() {
        return dashboardService.getDashboardSummary();
    }

    @GetMapping("/reports")
    public List<BorrowRecordDTO> getAllBorrowings() {
        return borrowRecordService.getAllBorrowRecords();
    }

    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<BorrowRecord> records = borrowRepository.findAll();
        List<TransactionDTO> dtoList = records.stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }



    @GetMapping("/book")
    public List<LibBookDto> getBooks() {
        return libBookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public LibBookDto getBook(@PathVariable Long id) {
        return libBookService.getBookById(id);
    }

    @PostMapping("/book")
    public LibBookDto addBook(@RequestBody LibBookDto bookDto) {
        return libBookService.addBook(bookDto);
    }

    @PatchMapping("/book/{id}")
    public LibBookDto updateBook(@PathVariable Long id, @RequestBody LibBookDto bookDto) {
        return libBookService.updateBook(id, bookDto);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        libBookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
