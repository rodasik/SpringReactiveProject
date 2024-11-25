package com.rodasik.springex.dal.entities;

import com.rodasik.springex.common.Default;
import com.rodasik.springex.common.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User extends BaseEntity {
    @Indexed
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private List<Role> roles;

    @Default
    public User(UUID id, String email, String firstName, String lastName, String username, List<Role> roles) {
        this.setId(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.roles = roles;
    }

    public User(User user) {
        this.setId(user.getId());
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        return Objects.equals(getId(), user.getId())
                && Objects.equals(email, user.email)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(username, user.username)
                && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() { return Objects.hash(getId(), email, firstName, lastName, username, roles); }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.getId())
                .append("email", this.getEmail())
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("username", username)
                .toString();
    }
}
