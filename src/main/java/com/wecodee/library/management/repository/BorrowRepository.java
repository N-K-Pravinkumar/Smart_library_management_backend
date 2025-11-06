package com.wecodee.library.management.repository;

import com.wecodee.library.management.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<BorrowRecord,Long> {

}
