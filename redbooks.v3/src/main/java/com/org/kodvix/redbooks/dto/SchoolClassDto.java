package com.org.kodvix.redbooks.dto;


import java.time.LocalDate;
import java.util.List;

import com.org.kodvix.redbooks.dao.ClassBookDao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassDto {

    private Long classId;
    private String className;
    //private LocalDate academicYear;

    private LocalDate academicYearStart;
    private LocalDate academicYearEnd;


    private Long schoolId;
    private List<ClassBookDao> classBookDaos;


}
