package com.duc.bookstore.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import javax.xml.catalog.Catalog;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Tiltle is required")
    @Size(max = 150 , message = "Title không quá 150 kí tự")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 150 , message = "Author name không quá 150 kí tự")
    @Column(nullable = false)
    private String author;

    @Column(unique = true)
    private String isbn; // International Standard Book Number – Số tiêu chuẩn quốc tế cho sách.Là số định danh duy nhất cho mỗi bản sách

    @Column(columnDefinition = "TEXT")
    private String description;


    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(precision = 15, scale = 4)
    private BigDecimal price;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category = Category.OTHER;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 1;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY) //tối ưu , chỉ load collection khi truy cập vào nó
    @JoinColumn(name = "created_by")
    private User createdBy;

    public enum Category{
        SCIENCE, NOVEL, TECHNOLOGY, HISTORY, BIOGRAPHY,CHILDREN, FANSTACY, OTHER
    }

    public Book() {
    }

    public Book(String title, String author, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    @PrePersist
    protected void OnCreate(){
        createdAt = LocalDateTime.now(); // thời điểm tạo mới
        updatedAt = LocalDateTime.now(); // lúc mới tạo thì updatedAt = createdAt
    }

    @PreUpdate
    protected void OnUpdate(){
        updatedAt = LocalDateTime.now(); // mỗi lần update thì cập nhật thời gian mới
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
