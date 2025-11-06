package com.wecodee.library.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long BorrowId;

    @ManyToOne
    @JoinColumn(name = "studentId",nullable = false)
    private long studentId;

    @ManyToOne
    @JoinColumn(name = "bookId",nullable = false)
    private long bookId;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private double fine;

}
