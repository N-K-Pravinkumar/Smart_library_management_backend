package com.wecodee.library.management.service;

import com.wecodee.library.management.dto.BookDto;
import com.wecodee.library.management.model.Book;

public class BookService {
    BookDto bookDto;
    public static BookDto fromEntity(Book book){
        return  new BookDto(
                book.getBookId(), book.getBookname(), book.getAuthor(), book.getCategory(),
                book.isBorrowed(), book.isBorrowed()
        );
    }


    public Book toEntity(){
        Book book=new Book();
        book.setBookId(bookDto.getBookId());
        book.setBookname(bookDto.getBookName());
        book.setAuthor(bookDto.getAuthor());
        book.setCategory(bookDto.getCategory());
        book.setReturned(bookDto.isReturned());
        book.setBorrowed(bookDto.isBorrowed());
        return book;
    }
}
