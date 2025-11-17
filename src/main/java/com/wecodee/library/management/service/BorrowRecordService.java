package com.wecodee.library.management.service;

import com.wecodee.library.management.dto.BorrowRecordDTO;
import com.wecodee.library.management.model.BorrowRecord;
import com.wecodee.library.management.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BorrowRecordService {

    private final BorrowRepository borrowRecordRepository;

    public BorrowRecordService(BorrowRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public List<BorrowRecordDTO> getAllBorrowRecords() {
        List<BorrowRecord> records = borrowRecordRepository.findAll();
        return records.stream()
                .map(BorrowRecordDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
