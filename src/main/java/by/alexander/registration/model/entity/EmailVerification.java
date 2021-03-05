package by.alexander.registration.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "email_verifications")
public final class EmailVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_verifications_id_seq")
    @SequenceGenerator(name = "email_verifications_id_seq", sequenceName = "email_verifications_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "token", nullable = false, updatable = false, unique=true)
    private String token;

    @Column(name = "issued", nullable = false, updatable = false)
    private LocalDateTime issued;

    @Column(name = "expires", nullable = false)
    private LocalDateTime expires;

    @Column(name = "confirmed")
    private LocalDateTime confirmed;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    protected EmailVerification() {
    }

    public EmailVerification(String token, LocalDateTime issued, LocalDateTime expires, LocalDateTime confirmed, Account account) {
        this.token = token;
        this.issued = issued;
        this.expires = expires;
        this.confirmed = confirmed;
        this.account = account;
    }

    /* Package-private for testing */

    EmailVerification(Long id, String token, LocalDateTime issued, LocalDateTime expires, LocalDateTime confirmed, Account account) {
        this.id = id;
        this.token = token;
        this.issued = issued;
        this.expires = expires;
        this.confirmed = confirmed;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getIssued() {
        return issued;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public LocalDateTime getConfirmed() {
        return confirmed;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailVerification that = (EmailVerification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(issued, that.issued) &&
                Objects.equals(expires, that.expires) &&
                Objects.equals(confirmed, that.confirmed) &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (issued != null ? issued.hashCode() : 0);
        result = 31 * result + (expires != null ? expires.hashCode() : 0);
        result = 31 * result + (confirmed != null ? confirmed.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", issued=" + issued +
                ", expires=" + expires +
                ", confirmed=" + confirmed +
                ", account=" + account +
                '}';
    }


    /* Builder */

    public static class Builder {

        private Long id;
        private String token;
        private LocalDateTime issued;
        private LocalDateTime expires;
        private LocalDateTime confirmed;
        private Account account;

        public Builder() {
        }

        private Builder(EmailVerification emailVerification) {
            id = emailVerification.id;
            token = emailVerification.token;
            issued = emailVerification.issued;
            expires = emailVerification.expires;
            confirmed = emailVerification.confirmed;
            account = emailVerification.account;
        }

        public static Builder from(EmailVerification emailVerification) {
            return new Builder(emailVerification);
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setIssued(LocalDateTime issued) {
            this.issued = issued;
            return this;
        }

        public Builder setExpires(LocalDateTime expires) {
            this.expires = expires;
            return this;
        }

        public Builder setConfirmed(LocalDateTime confirmed) {
            this.confirmed = confirmed;
            return this;
        }

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public EmailVerification build() {
            return new EmailVerification(id, token, issued, expires, confirmed, account);
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
                    Objects.equals(token, builder.token) &&
                    Objects.equals(issued, builder.issued) &&
                    Objects.equals(expires, builder.expires) &&
                    Objects.equals(confirmed, builder.confirmed) &&
                    Objects.equals(account, builder.account);
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (token != null ? token.hashCode() : 0);
            result = 31 * result + (issued != null ? issued.hashCode() : 0);
            result = 31 * result + (expires != null ? expires.hashCode() : 0);
            result = 31 * result + (confirmed != null ? confirmed.hashCode() : 0);
            result = 31 * result + (account != null ? account.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "id=" + id +
                    ", token='" + token + '\'' +
                    ", issued=" + issued +
                    ", expires=" + expires +
                    ", confirmed=" + confirmed +
                    ", account=" + account +
                    '}';
        }
    }
}
