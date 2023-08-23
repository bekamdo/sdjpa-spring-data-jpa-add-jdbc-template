package com.bekamdo.jdbc.dao;

import com.bekamdo.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"com.bekamdo.jdbc.dao"})
class BookDaoImplTest {
    @Autowired
    BookDao bookDao;


    @Test
    void getById() {
        Book book = bookDao.getById(3L);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(4);
    }

    @Test
    void findBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void saveNewBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);

        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void findAllBooksPage1_pageable(){
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0,10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }

    @Test
    void findAllBooksPage1_sortByTitle(){
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0,10,
                Sort.by(Sort.Order.asc("title"))));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }
    @Test
    void findAllBooksPage2_pageable(){
        List<Book> books = bookDao.findAllBooks(PageRequest.of(1,10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void findAllBooksPage1(){
        List<Book> books = bookDao.findAllBooks(10,0);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }
    @Test
    void findAllBooksPage2(){
        List<Book> books = bookDao.findAllBooks(10,10);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void deleteBookById() {

        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            bookDao.getById(saved.getId());
        });
    }

}