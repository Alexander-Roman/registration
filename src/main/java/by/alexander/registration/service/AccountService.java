package by.alexander.registration.service;

import by.alexander.registration.model.entity.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Account singUpAccount(Account account);

    Account enableAccount(Account account);
}
