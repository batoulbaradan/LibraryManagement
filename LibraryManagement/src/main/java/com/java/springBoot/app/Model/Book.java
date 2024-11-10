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

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull(message = "Published year is required")
    @Min(value = 1450, message = "Published year must be a valid year")
    private Integer publishedYear;

    @NotBlank(message = "ISBN is required")
    @Column(unique = true)
    private String isbn;

    @NotNull(message = "Total copies is required")
    @Min(value = 1, message = "There must be at least 1 copy")
    private Integer totalCopies;

    private Integer availableCopies;

    public boolean isAvailable() {
        return availableCopies != null && availableCopies > 0;
    }
}
