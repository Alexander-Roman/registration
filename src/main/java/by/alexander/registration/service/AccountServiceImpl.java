package by.alexander.registration.service;

import by.alexander.registration.model.entity.Account;
import by.alexander.registration.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account singUpAccount(Account account) {
        String password = account.getPassword();
        String passwordEncoded = passwordEncoder.encode(password);
        Account changed = Account.Builder.from(account)
                .setPassword(passwordEncoded)
                .build();
        return accountRepository.save(changed);
    }

    @Override
    public Account enableAccount(Account account) {
        Account enabled = Account.Builder.from(account)
                .setEnabled(true)
                .build();
        return accountRepository.save(enabled);
    }
}
