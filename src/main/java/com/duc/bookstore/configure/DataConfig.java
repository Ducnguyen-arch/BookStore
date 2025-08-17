package com.duc.bookstore.configure;

import com.duc.bookstore.entity.Book;
import com.duc.bookstore.entity.User;
import com.duc.bookstore.service.BookService;
import com.duc.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.Set;

public class DataConfig implements CommandLineRunner {


    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        InitUser();
        initBooks();
    }

    private void InitUser() {
//         Check if admin user already exists
        if (!userService.existByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword("admin");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setRoles(Set.of(User.Role.ADMIN, User.Role.USER));
            userService.save(admin);
            System.out.println("Admin user created: username: admin, password: admin123");
        }

        // Check if OPERATOR user already exists
        if (!userService.existByUsername("OPERATOR")) {
            User OPERATOR = new User();
            OPERATOR.setUsername("OPERATOR");
            OPERATOR.setEmail("OPERATOR@example.com");
            OPERATOR.setPassword("mod123");
            OPERATOR.setFirstName("OPERATOR");
            OPERATOR.setLastName("User");
            OPERATOR.setRoles(Set.of(User.Role.OPERATOR, User.Role.USER));
            userService.save(OPERATOR);
            System.out.println("OPERATOR user created: OPERATOR/mod123");
        }

        // Check if regular user already exists
        if (!userService.existByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword("user123");
            user.setFirstName("Java");
            user.setLastName("User");
            user.setRoles(Set.of(User.Role.USER));
            userService.save(user);
            System.out.println("Regular user created: user/user123");
        }
    }

    private void initBooks() {
        // Check if books already exist
        if (bookService.findAll().isEmpty()) {
            User admin = userService.findByUsername("admin").orElse(null);

            // Create sample books
            Book book1 = new Book();
            book1.setTitle("Clean Code: Agile Software Craftsmanship");
            book1.setAuthor("Robert C. Martin");
            book1.setIsbn("978-0132350884");
            book1.setDescription("A handbook of agile software craftsmanship");
            book1.setPrice(new BigDecimal("7500000"));
            book1.setPublicationYear(2008);
            book1.setCategory(Book.Category.TECHNOLOGY);
            book1.setStockQuantity(15);
            book1.setCreatedBy(admin);
            bookService.saveBook(book1);

            Book book3 = new Book();
            book3.setTitle("A Brief History of Time");
            book3.setAuthor("Stephen Hawking");
            book3.setIsbn("978-0553380163");
            book3.setDescription("From the Big Bang to Black Holes");
            book3.setPrice(new BigDecimal("450000"));
            book3.setPublicationYear(1988);
            book3.setCategory(Book.Category.SCIENCE);
            book3.setStockQuantity(10);
            book3.setCreatedBy(admin);
            bookService.saveBook(book3);


            Book book5 = new Book();
            book5.setTitle("The Art of War");
            book5.setAuthor("Sun Tzu");
            book5.setIsbn("978-1599869773");
            book5.setDescription("Ancient Chinese military treatise");
            book5.setPrice(new BigDecimal("2000000"));
            book5.setPublicationYear(-500); // Ancient book
            book5.setCategory(Book.Category.HISTORY);
            book5.setStockQuantity(30);
            book5.setCreatedBy(admin);
            bookService.saveBook(book5);

            Book book6 = new Book();
            book6.setTitle("Steve Jobs");
            book6.setAuthor("Walter Isaacson");
            book6.setIsbn("978-1451648539");
            book6.setDescription("Biography of Apple co-founder Steve Jobs");
            book6.setPrice(new BigDecimal("5000000"));
            book6.setPublicationYear(2011);
            book6.setCategory(Book.Category.BIOGRAPHY);
            book6.setStockQuantity(12);
            book6.setCreatedBy(admin);
            bookService.saveBook(book6);
            System.out.println("Create Book Successfully!");
        }
    }
}
