package com.java.springBoot.app.Model;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @NotNull(message = "Borrow date is required")
    private LocalDate borrowDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private LocalDate returnDate;

}
