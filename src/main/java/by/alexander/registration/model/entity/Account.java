package by.alexander.registration.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public final class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_id_seq")
    @SequenceGenerator(name = "accounts_id_seq", sequenceName = "accounts_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "blocked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean blocked = false;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean enabled = false;

    protected Account() {
    }

    public Account(String email,
                   String username,
                   String name,
                   String password,
                   Role role,
                   Boolean blocked,
                   Boolean enabled) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
        this.enabled = enabled;
    }

    /* Package-private for testing */
    Account(Long id,
            String email,
            String username,
            String name,
            String password,
            Role role,
            Boolean blocked,
            Boolean enabled) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
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

    public Role getRole() {
        return role;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(email, account.email) &&
                Objects.equals(username, account.username) &&
                Objects.equals(name, account.name) &&
                Objects.equals(password, account.password) &&
                role == account.role &&
                Objects.equals(blocked, account.blocked) &&
                Objects.equals(enabled, account.enabled);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                ", enabled=" + enabled +
                '}';
    }


    /* Builder */

    public static class Builder {

        private Long id;
        private String email;
        private String username;
        private String name;
        private String password;
        private Role role;
        private Boolean blocked;
        private Boolean enabled;

        public Builder() {
        }

        private Builder(Account account) {
            id = account.id;
            email = account.email;
            username = account.username;
            name = account.name;
            password = account.password;
            role = account.role;
            blocked = account.blocked;
            enabled = account.enabled;
        }

        public static Builder from(Account account) {
            return new Builder(account);
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setBlocked(Boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public Builder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Account build() {
            return new Account(id, email, username, name, password, role, blocked, enabled);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Builder builder = (Builder) o;
            return Objects.equals(id, builder.id) &&
                    Objects.equals(email, builder.email) &&
                    Objects.equals(username, builder.username) &&
                    Objects.equals(name, builder.name) &&
                    Objects.equals(password, builder.password) &&
                    role == builder.role &&
                    Objects.equals(blocked, builder.blocked) &&
                    Objects.equals(enabled, builder.enabled);
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (email != null ? email.hashCode() : 0);
            result = 31 * result + (username != null ? username.hashCode() : 0);
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (password != null ? password.hashCode() : 0);
            result = 31 * result + (role != null ? role.hashCode() : 0);
            result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
            result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", username='" + username + '\'' +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", role=" + role +
                    ", blocked=" + blocked +
                    ", enabled=" + enabled +
                    '}';
        }
    }
}
