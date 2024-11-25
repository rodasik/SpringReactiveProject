package com.rodasik.springex.api.mappers;

import com.rodasik.springex.api.dto.CityDTO;
import com.rodasik.springex.api.pagination.PaginatedResponse;
import com.rodasik.springex.api.requests.CreateCityRequest;
import com.rodasik.springex.api.requests.CreateCountryRequest;
import com.rodasik.springex.api.requests.UpdateCityRequest;
import com.rodasik.springex.api.requests.UpdateCountryRequest;
import com.rodasik.springex.config.GlobalMapperConfig;
import com.rodasik.springex.dal.entities.City;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface CityMapper {
    default PaginatedResponse<CityDTO> mapPaginatedResponse(Page<City> page) {
        PaginatedResponse<CityDTO> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setContent(toListDTO(page.getContent()));
        paginatedResponse.setTotalElements(page.getTotalElements());
        paginatedResponse.setTotalPages(page.getTotalPages());
        return paginatedResponse;
    }

    List<CityDTO> toListDTO(List<City> cityList);

    CityDTO toDTO(City city);
    City toEntity(CityDTO cityDTO);

    City createRequestToEntity(CreateCityRequest cityDTO);
    City updateRequestToEntity(UpdateCityRequest cityDTO);
}
