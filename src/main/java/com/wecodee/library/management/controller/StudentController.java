package com.wecodee.library.management.controller;

import com.wecodee.library.management.dto.BookDto;
import com.wecodee.library.management.model.BorrowRecord;
import com.wecodee.library.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/home")
    public  String home(){
        return "Welcome to Smart library Management";
    }

    @GetMapping("/books")
    public List<BookDto> booklist(){
        return studentService.getListOfBook();
    }

    @PostMapping("/borrow/{bookId}")
    public String borrowBook(@PathVariable long bookId,@RequestParam long studentId){
        return studentService.borrowBook(bookId,studentId);
    }

    @PostMapping("/return/{bookId}")
    public  String returnBook(@PathVariable long bookId,@RequestParam long studentId){
        return studentService.returnBook(bookId,studentId);
    }
    @GetMapping("/borrow-return/{studentId}")
    public List<BorrowRecord> getBorrowedHistory(@RequestParam long studentId){
        return  studentService.getBorrowHistory(studentId);
    }

    @GetMapping("/report/{studentId}")
    public List<BorrowRecord> getReport(@RequestParam long studentId){
        return studentService.getBorrowHistory(studentId);
    }
}
