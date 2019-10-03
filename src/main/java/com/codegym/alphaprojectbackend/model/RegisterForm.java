package com.codegym.alphaprojectbackend.model;

import javax.validation.constraints.Size;

public class RegisterForm {
    private String email;

    @Size(min = 8, max = 15)
    private String password;

    public RegisterForm() {
    }

    public RegisterForm(String email, @Size(min = 8, max = 15) String password) {
        this.email = email;
        this.password = password;
    }

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
}
