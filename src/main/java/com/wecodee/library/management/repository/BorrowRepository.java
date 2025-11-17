package com.wecodee.library.management.repository;

import com.wecodee.library.management.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<BorrowRecord,Long> {


    List<BorrowRecord> findByUser_UserId(Long userId);

    long countByUser_UserIdAndReturnDateIsNull(Long userId);

    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.user.userId = :studentId")
    long countByUserId(long studentId);

    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.user.userId = :studentId AND br.returnDate IS NULL")
    long countCurrentlyBorrowed(long studentId);

    @Query("SELECT COALESCE(SUM(br.fine), 0) FROM BorrowRecord br WHERE br.user.userId = :studentId")
    double totalFineByUserId(long studentId);

    Optional<BorrowRecord> findByUser_UserIdAndBook_BookIdAndReturnDateIsNull(Long userId, Long bookId);

}
