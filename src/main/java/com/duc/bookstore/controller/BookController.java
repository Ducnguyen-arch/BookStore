package com.duc.bookstore.controller;

import com.duc.bookstore.entity.Book;
import com.duc.bookstore.entity.User;
import com.duc.bookstore.service.BookService;
import com.duc.bookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

//  GET /books?page=1&sortBy=price&title=JAVABOOK

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listBooks(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int sizeBook,
                            @RequestParam(defaultValue = "title") String sortBy,
                            @RequestParam(defaultValue = "asc") String sortByDir,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) Book.Category category,
                            Model model) {

        //Pageable obj & Sorting Obj
        Sort.Direction sortDirection = sortByDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, sizeBook, Sort.by(sortDirection, sortBy));

        //Service Call
        Page<Book> listBooks = bookService.searchBook(title, author, category, pageable);

        model.addAttribute("books", listBooks); //object & pagination
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("category", category);
        model.addAttribute("categories", Book.Category.values()); //enum value for dropdown in thymleaf
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortByDir);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String displayBook(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "books/view";
        }
        return "redirect:/books";
    }

   /* 1. Get /book/add
      2.Spring Security check role ADMIN or OPERATOR
      3.If authorize >>> run method addBookToForm
      4.Create empty Book >> Get all categories ( enum values )
      5.Add Book to Model
      6.Return "books/adđ"
      7.Thymleaf render
    */

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    public String addBookToForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", Book.Category.values()); //Trả về array tất cả các enum values
        return "books/add";
    }

    /*1. User submit form POST /books/add
      2.Spring Security check role ADMIN or OPERATOR
      3.Spring MVC bind data to Book obj
      4.@Valid trigger bean
      5.If valid error >> add categories to Model >> Return "/books/add" with message error
      6.If valid OK >> Get current User from Authen
      7.Set book.createBy = currentUser >> Save Book into Service -> repo -> DB
      8.Add flash message >> redirect /books ( list books page )
    */

    //POST xử lý form → Redirect → GET hiển thị result
    //Add Book
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    public String addBook(@Valid @ModelAttribute("book") Book book, //@Valid: trigger bean validation trong Book
                          BindingResult bindingResult,
                          Authentication authentication,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Book.Category.values());
            return "books/add";
        }

        Optional<User> currentUser = userService.findByUsername(authentication.getName());
//      currentUser.ifPresent(user -> book.setCreatedBy(user));
        currentUser.ifPresent(book::setCreatedBy); //xem ai đã tạo book

        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("message", "Thêm sách thành công!");
        return "redirect:/books";
    }

    //Edit Book
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    public String editBookToForm(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("categories", Book.Category.values());
            return "books/edit";
        }
        return "redirect:/books";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    public String editBook(@PathVariable Long id,
                           @Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Book.Category.values());
            return "/books/edit";
        }

        book.setId(id);
        bookService.updateBook(book);
        redirectAttributes.addFlashAttribute("message", "Đã sửa sách thành công!");
        return "redirect:/books";

    }

    //Delete Book by Id
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Long id,RedirectAttributes redirectAttributes){
        bookService.deleteById(id);
        redirectAttributes.addFlashAttribute("message" , "Xóa sách thành công");
        return "redirect:/books";
    }
}
