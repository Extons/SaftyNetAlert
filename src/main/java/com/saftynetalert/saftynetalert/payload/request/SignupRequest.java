package com.saftynetalert.saftynetalert.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

/**
 * @author ufhopla
 * on 30/05/2022.
 */

@Data
public class SignupRequest {

    @NotBlank
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private Date birthdate;

    @NotBlank
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
