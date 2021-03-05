package by.alexander.registration.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class RegistrationDto implements Serializable {

    private static final Long serialVersionUID = 1L;

    private final String email;
    private final String username;
    private final String name;
    private final String password;
    private final String passwordConfirm;

    public RegistrationDto(String email,
                           String username,
                           String name,
                           String password,
                           String passwordConfirm) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistrationDto that = (RegistrationDto) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(username, that.username) &&
                Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(passwordConfirm, that.passwordConfirm);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passwordConfirm != null ? passwordConfirm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
