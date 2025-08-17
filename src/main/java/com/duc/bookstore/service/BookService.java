package com.duc.bookstore.service;

import com.duc.bookstore.entity.Book;
import com.duc.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }

    public Optional<Book> findByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Page<Book> findAll(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }


    public List<Book> searchByCategory(Book.Category category){
        return bookRepository.findByCategory(category);
    }

    public List<Book> searchPriceOnRange(BigDecimal minPrice, BigDecimal maxPrice){
        return bookRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public Page<Book> searchBook(String title, String author, Book.Category category, Pageable pageable){
        return bookRepository.findBooksWithFilters(title, author, category, pageable);
    }

    public List<Book> findAvailableBooks() {
        return bookRepository.findAvailableBook();
    }

    public long countBookByUser(Long uerId){
        return bookRepository.countBookByUser(uerId);
    }

    public Book updateBook(Book book){
        return bookRepository.save(book);
    }





}
