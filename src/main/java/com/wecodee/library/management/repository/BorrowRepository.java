package com.wecodee.library.management.repository;

import com.wecodee.library.management.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<BorrowRecord,Long> {

    List<BorrowRecord> findByStudentId(long studentId);
}
