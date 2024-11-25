package com.rodasik.springex.api.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SearchRequest {
    private UUID id;
    private String filter;
    private String sortParam;
    private String sortOrder;
    private Integer pageNumber;
    private Integer pageSize;
}
