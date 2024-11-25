package com.rodasik.springex.api.mappers;

import com.rodasik.springex.api.dto.UserDTO;
import com.rodasik.springex.api.requests.CreateUserRequest;
import com.rodasik.springex.api.requests.UpdateUserRequest;
import com.rodasik.springex.config.GlobalMapperConfig;
import com.rodasik.springex.dal.entities.User;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public abstract class UserMapperStruct {
    public abstract UserDTO toDTO(User user);
    public abstract User toDomain(UserDTO user);

    public abstract User createRequestToEntity(CreateUserRequest createUserRequest);
    public abstract User updateRequestToEntity(UpdateUserRequest updateUserRequest);
}

