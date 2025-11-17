package com.wecodee.library.management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long bookId;
    private String bookName;
    private String author;
    private String category;
    private boolean borrowed;
    private boolean returned;

}
