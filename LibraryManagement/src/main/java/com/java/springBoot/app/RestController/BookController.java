package com.java.springBoot.app.RestController;


import com.java.springBoot.app.Class.ResponseStatus;
import com.java.springBoot.app.Class.Response.APIResponse;
import com.java.springBoot.app.Class.Response.DTO.UserDTO;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
    private BookService bookService;

    @GetMapping
    public APIResponse<Book> getAllBooks() {
    	APIResponse apiResponse= new APIResponse<Book>();
    	List<Book> books = bookService.getAllBooks();
    	apiResponse = APIResponse.successList(books);
        return apiResponse;
    }

    @GetMapping("/{id}")
    public APIResponse<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
    	APIResponse apiResponse= new APIResponse<Book>();
    	apiResponse = APIResponse.success(book);
        return apiResponse;
 }

    @PostMapping
    public APIResponse<Book> createBook(@Valid @RequestBody Book book) {
    	Book res_book= bookService.saveBook(book);
    	APIResponse apiResponse= new APIResponse<Book>();
    	apiResponse = APIResponse.success(res_book);
        return apiResponse;

    }

    @PutMapping("/{id}")
    public APIResponse<Book> updateBook(@Valid @PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> book = bookService.getBookById(id);
        APIResponse<Book> apiResponse = new APIResponse<>();

        if (book.isPresent()) {
            bookDetails.setId(id);
            apiResponse = APIResponse.success(bookService.updateBook(bookDetails));
            return apiResponse;
        } else {
            return APIResponse.error(ResponseStatus.NOT_FOUND); 
        }
    }

    @DeleteMapping("/{id}")
    public APIResponse<Void> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        
        if (isDeleted) {
            return APIResponse.success(null); 
        } else {
            return APIResponse.error(ResponseStatus.NOT_FOUND); 
        }
    }
}
