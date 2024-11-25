package com.rodasik.springex.bll;

import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.common.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface FinderService {
    default Pageable preparePageable(SearchRequest searchRequest) {
        Sort sort = Sort.by(searchRequest.getSortParam());
        sort = searchRequest.getSortOrder().equals(SortOrder.ASCENDING.toString()) ? sort.ascending() : sort.descending();
        return PageRequest.of(searchRequest.getPageNumber(), searchRequest.getPageSize(), sort);
    }
}
