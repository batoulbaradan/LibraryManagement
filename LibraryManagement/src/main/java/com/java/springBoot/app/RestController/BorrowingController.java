package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Class.Response.APIResponse;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Model.BorrowingRecord;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Service.BookService;
import com.java.springBoot.app.Service.BorrowingService;
import com.java.springBoot.app.Service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public APIResponse<String> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        Optional<Patron> patronOptional = patronService.getPatronById(patronId);

        if (bookOptional.isEmpty() || patronOptional.isEmpty()) {
            return APIResponse.error(404, "Book or Patron not found");
        }

        Book book = bookOptional.get();
        if (book.getAvailableCopies() <= 0) {
            return APIResponse.error(400, "No copies available for borrowing");
        }

        BorrowingRecord borrowing = borrowingService.borrowBook(book, patronOptional.get());
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.updateBook(book);

        return APIResponse.success("Book borrowed successfully");
    }

    @PostMapping("/return/{borrowingId}")
    public APIResponse<String> returnBook(@PathVariable Long borrowingId) {
        Optional<BorrowingRecord> borrowingOptional = borrowingService.getBorrowingById(borrowingId);

        if (borrowingOptional.isEmpty()) {
            return APIResponse.error(404, "Borrowing record not found");
        }

        BorrowingRecord borrowing = borrowingOptional.get();
        Book book = borrowing.getBook();

        borrowingService.returnBook(borrowing);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.updateBook(book);

        return APIResponse.success("Book returned successfully");
    }
}
