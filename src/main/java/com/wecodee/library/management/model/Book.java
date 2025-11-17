package com.wecodee.library.management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @Column(nullable = false)
    private String bookname;

    @Column(nullable = false)
    private String author;

    private String category;
    private boolean isBorrowed;
    private boolean isReturned;
}
