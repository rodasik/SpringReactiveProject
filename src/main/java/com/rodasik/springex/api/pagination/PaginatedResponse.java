package com.rodasik.springex.api.pagination;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    Long totalElements;
    List<T> content;
    int totalPages;
}
