package com.org.kodvix.redbooks.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.org.kodvix.redbooks.dao.Category;
import com.org.kodvix.redbooks.dao.ContactDao;
import com.org.kodvix.redbooks.dao.SchoolClassDao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SchoolDto {

    private Long schoolId;

    @NotNull(message ="School Name  Required")
    @Size(min=8, max=40, message = "Min 5 to Max 25 characters is required")
    private String schoolName;

//    @NotNull(message = "Email is required")
//    @Email(message ="Enter a valid email")
//    private String email;

//    @Pattern(
//            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
//            message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number, 1 special character and be at least 8 characters long")
//    @NotNull(message = "password is required")
//    private String schoolPassword;

    @NotNull(message = "Enter a valid Type")
    private String schoolType;

    @NotNull(message = "Enter a valid Category")
    private Category category;

    @NotNull(message = "Enter Medium")
    private String medium;

    @NotNull(message = "Enter Board")
    private String board;

    private String socialMediaUrl;
    private Date createdAt;
    //	private String createdBy;    optional fields
    private Date updatedAt;
    //	private String updatedBy;
    private String description;

    private ContactDao contact;

    //	@JsonIgnore
    private List<SchoolClassDao> classes = new ArrayList<>();

    private List<PublisherDto> recommendedPublishers;


}
