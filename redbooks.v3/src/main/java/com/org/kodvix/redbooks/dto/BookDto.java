package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Publisher ID is required")
    private Long publisherId;
    
    @NotNull(message = "class Name is required")
    private String  className;

    @NotNull(message = "Publication year is required")
    @Min(value = 1000, message = "Publication year should be valid")
    private Integer publicationYear;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Book price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private Double bookPrice;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Number of pages is required")
    @Min(value = 1, message = "Pages must be at least 1")
    private Integer numberOfPages;

    @NotBlank(message = "Edition is required")
    private String edition;

    private MultipartFile orderDocument;
}
