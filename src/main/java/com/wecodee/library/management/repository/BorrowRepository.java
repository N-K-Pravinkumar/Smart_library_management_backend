package com.wecodee.library.management.repository;

import com.wecodee.library.management.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<BorrowRecord,Long> {

    List<BorrowRecord> findByUserId(Long UserId);
    Optional<BorrowRecord> findByBook_BookIdAndUserIdAndReturnDateIsNull(Long bookId, Long UserId);

}
