package com.duc.bookstore.repository;


import com.duc.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByCategory(Book.Category category);

    List<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice );

    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findById(Long id);

    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%s', :title, '%'))) AND " +
            "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%s', :author, '%'))) AND" +
            ":category IS NULL OR (b.category = :category)")
    Page<Book> findBooksWithFilters(@Param("title") String title,
                                    @Param("author") String author,
                                    @Param("category") Book.Category category, Pageable pageable);

    @Query("SELECT b from Book b where b.stockQuantity > 0")
    List<Book> findAvailableBook();

    @Query("SELECT count (b) from Book b where b.createdBy.id = :userId")
    long countBookByUser(@Param("userId") Long userId);

}
