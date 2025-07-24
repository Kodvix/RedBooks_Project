package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class OrderRequest {
    @NotNull
    private Long classId;
}
