package com.rodasik.springex.api.dto;

import com.rodasik.springex.common.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserDTO extends BaseDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private List<Role> roles;
}
