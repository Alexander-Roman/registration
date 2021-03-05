package by.alexander.registration.service;

import by.alexander.registration.model.entity.Account;
import by.alexander.registration.model.entity.Permission;
import by.alexander.registration.model.entity.Role;
import by.alexander.registration.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> loaded = accountRepository.findByEmail(username);
        if (loaded.isEmpty()) {
            loaded = accountRepository.findByUsername(username);
        }
        if (loaded.isEmpty()) {
            String errorMessage = String.format("User %s does not exists!", username);
            throw new UsernameNotFoundException(errorMessage);
        }
        Account account = loaded.get();

        String password = account.getPassword();
        Boolean enabled = account.getEnabled();
        Boolean locked = account.getBlocked();
        Role role = account.getRole();
        Set<GrantedAuthority> grantedAuthorities = retrieveGrantedAuthoritySetFromRole(role);

        return new User(
                username,
                password,
                enabled,
                true,
                true,
                !locked,
                grantedAuthorities
        );
    }

    private Set<GrantedAuthority> retrieveGrantedAuthoritySetFromRole(Role role) {
        Set<Permission> permissions = role.getPermissions();
        Set<GrantedAuthority> authorities = permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority("PERMISSION_" + permission.name()))
                .collect(Collectors.toSet());
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.name());
        authorities.add(roleAuthority);
        return authorities;
    }
}
