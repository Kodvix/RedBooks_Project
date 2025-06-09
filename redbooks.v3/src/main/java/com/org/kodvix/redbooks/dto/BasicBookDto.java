package com.org.kodvix.redbooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicBookDto {

    private Long bookId;


    private String bookName;


    private String author;


    private String publisher;


    private Integer edition;

    private Integer publishedYear;
    //    YEAR
//
    private String subject;
    //    SUBJECT
//
    private String isbn;
//    ISBN
//


//    CATEGORY

    private String description;


    private String language;


    private byte[] image;

    private Long classId;

    private Long masterBookId;

}
