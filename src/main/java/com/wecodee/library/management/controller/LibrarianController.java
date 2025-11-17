package com.wecodee.library.management.controller;

import com.wecodee.library.management.model.BorrowRecord;
import com.wecodee.library.management.model.User;
import com.wecodee.library.management.service.LibrarianService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {

    public LibrarianService librarianService;

    @GetMapping("/home")
    public Map<String, Object> home() {
        return librarianService.getDashboardSummary();
    }

    @GetMapping("/students")
    public List<User> getAllStudents(@RequestParam(required = false) String sortBy) {
        return librarianService.getAllStudentsSorted(sortBy);
    }


    @GetMapping("/borrow-return")
    public List<BorrowRecord> getBorrowReturnRecords(@RequestParam(required = false) Long studentId) {
        if (studentId != null) {
            return librarianService.getBorrowHistoryForStudent(studentId);
        } else {
            return librarianService.getAllBorrowRecords();
        }
    }

    @PostMapping("/report")
    public List<BorrowRecord> getReportByStudents(@RequestBody List<Long> studentIds) {
        return librarianService.getBorrowRecordsByStudents(studentIds);
    }
}
