package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AssignBooksRequest {

    private List<BookAssignmentDto> books;
}
