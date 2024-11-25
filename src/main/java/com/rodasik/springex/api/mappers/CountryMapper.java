package com.rodasik.springex.api.mappers;

import com.rodasik.springex.api.dto.CountryDTO;
import com.rodasik.springex.api.pagination.PaginatedResponse;
import com.rodasik.springex.api.requests.CreateCountryRequest;
import com.rodasik.springex.api.requests.UpdateCountryRequest;
import com.rodasik.springex.config.GlobalMapperConfig;
import com.rodasik.springex.dal.entities.Country;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface CountryMapper {
    default PaginatedResponse<CountryDTO> mapPaginatedResponse(Page<Country> response) {
        PaginatedResponse<CountryDTO> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setTotalElements(response.getTotalElements());
        paginatedResponse.setTotalPages(response.getTotalPages());
        paginatedResponse.setContent(toListDTO(response.getContent()));
        return paginatedResponse;
    }
    List<CountryDTO> toListDTO(List<Country> countryList);

    CountryDTO toDTO(Country country);
    Country toEntity(CountryDTO countryDTO);

    Country createRequestToEntity(CreateCountryRequest createCountryRequest);
    Country updateRequestToEntity(UpdateCountryRequest updateCountryRequest);
}

