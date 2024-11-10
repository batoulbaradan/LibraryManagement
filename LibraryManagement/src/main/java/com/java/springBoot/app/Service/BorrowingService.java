package com.java.springBoot.app.Service;

import com.java.springBoot.app.Model.BorrowingRecord;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    public Optional<BorrowingRecord> getBorrowingById(Long id) {
        return borrowingRepository.findById(id);
    }

    public BorrowingRecord borrowBook(Book book, Patron patron) {
        BorrowingRecord borrowing = new BorrowingRecord();
        borrowing.setBook(book);
        borrowing.setPatron(patron);
        return borrowingRepository.save(borrowing);
    }

    public void returnBook(BorrowingRecord borrowing) {
        borrowingRepository.delete(borrowing);
    }
}
