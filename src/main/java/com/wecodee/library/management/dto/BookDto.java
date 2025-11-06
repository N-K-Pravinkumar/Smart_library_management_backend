package com.wecodee.library.management.dto;

import com.wecodee.library.management.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long bookId;
    private String bookName;
    private String author;
    private String category;
    private boolean isBorrowed;
    private boolean isReturned;

}
