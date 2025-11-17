package com.wecodee.library.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentHomeDTO {
    private long totalBooks;
    private long totalBorrowedCount;      // all-time borrowed
    private long currentlyBorrowedCount;  // currently not returned
    private double totalFine;             // total fine accumulated
}
