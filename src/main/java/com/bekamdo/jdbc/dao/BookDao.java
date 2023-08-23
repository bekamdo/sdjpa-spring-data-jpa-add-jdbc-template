package com.bekamdo.jdbc.dao;

import com.bekamdo.jdbc.domain.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jt on 8/29/21.
 */
public interface BookDao {

    List<Book> findAllBooksSortByTitle(Pageable pageable);

    List<Book> findAllBooks(Pageable pagable);

    List<Book> findAllBooks(int pageSize,int offset);

    List<Book> findAllBooks();
    
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
