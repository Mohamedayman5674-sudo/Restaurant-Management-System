package com.spring.boot.restaurant.services.impl.security;

import com.spring.boot.restaurant.controllers.vm.AccountResponseVm;
import com.spring.boot.restaurant.dto.security.AccountDto;
import com.spring.boot.restaurant.mappers.security.AccountMapper;
import com.spring.boot.restaurant.mappers.security.RoleMapper;
import com.spring.boot.restaurant.models.security.Account;
import com.spring.boot.restaurant.models.security.AccountDetails;
import com.spring.boot.restaurant.models.security.Role;
import com.spring.boot.restaurant.repositories.security.AccountRepo;
import com.spring.boot.restaurant.services.security.AccountService;
import com.spring.boot.restaurant.services.security.RoleService;
import com.spring.boot.restaurant.utils.RoleEnum;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    // =========================
    // GET ALL ACCOUNTS
    // OLD METHOD
    // =========================

    @Override
    @Cacheable(
            value = "accounts",
            key = "'all'"
    )
    public List<AccountDto> getAccounts() {

        try {

            List<Account> users =
                    accountRepo.findAllByOrderByIdAsc();

            if (users.isEmpty()) {

                throw new SystemException(
                        "empty.accounts"
                );
            }

            return users.stream()
                    .map(
                            AccountMapper.ACCOUNT_MAPPER
                                    ::toAccountDto
                    )
                    .collect(Collectors.toList());

        } catch (SystemException e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    // =========================
    // GET ALL ACCOUNTS
    // PAGINATION
    // =========================

    @Override
    @Cacheable(
            value = "accounts",
            key = "'page-' + #page + '-' + #size"
    )
    public AccountResponseVm getAccounts(
            int page,
            int size) {

        try {

            if (page < 1) {

                throw new SystemException(
                        "error.min.one.page"
                );
            }

            if (size < 1) {

                throw new SystemException(
                        "error.min.one.size"
                );
            }

            Pageable pageable =
                    PageRequest.of(
                            page - 1,
                            size
                    );

            Page<Account> accountsPage =
                    accountRepo.findAllByOrderByIdAsc(
                            pageable
                    );

            if (accountsPage.isEmpty()) {

                throw new SystemException(
                        "empty.accounts"
                );
            }

            List<AccountDto> accounts =
                    accountsPage
                            .getContent()
                            .stream()
                            .map(
                                    AccountMapper.ACCOUNT_MAPPER
                                            ::toAccountDto
                            )
                            .collect(Collectors.toList());

            return new AccountResponseVm(
                    accounts,
                    accountsPage.getTotalElements()
            );

        } catch (SystemException e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    // =========================
    // CREATE ACCOUNT
    // =========================

    @Override
    @CacheEvict(
            value = "accounts",
            allEntries = true
    )
    public AccountDto createAccount(
            AccountDto accountDto) {

        try {

            validateCreateAccount(accountDto);

            accountDto.setEnabled("1");

            Account user =
                    AccountMapper.ACCOUNT_MAPPER
                            .toAccount(accountDto);

            user.setPassword(
                    passwordEncoder.encode(
                            accountDto.getPassword()
                    )
            );

            if (Objects.nonNull(
                    user.getAccountDetails()
            )) {

                user.getAccountDetails()
                        .setAccount(user);
            }

            initRoleToUser(user);

            user = accountRepo.save(user);

            return AccountMapper.ACCOUNT_MAPPER
                    .toAccountDto(user);

        } catch (SystemException e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    // =========================
    // INIT USER ROLE
    // =========================

    private void initRoleToUser(
            Account user) {

        Role role =
                RoleMapper.ROLE_MAPPER.toRole(
                        roleService.findByRole(
                                RoleEnum.USER.toString()
                        )
                );

        List<Role> roles =
                user.getRoles();

        if (Objects.isNull(roles)) {

            roles = new ArrayList<>();
        }

        roles.add(role);

        user.setRoles(roles);
    }


    // =========================
    // VALIDATE CREATE
    // =========================

    private void validateCreateAccount(
            AccountDto accountDto)
            throws SystemException {

        if (Objects.nonNull(
                accountDto.getId()
        )) {

            throw new SystemException(
                    "id.must_be.null"
            );
        }

        if (Objects.nonNull(
                getAccountByUsername(
                        accountDto.getUsername()
                )
        )) {

            throw new SystemException(
                    "account.exists"
            );
        }
    }


    // =========================
    // UPDATE ACCOUNT
    // =========================

    @Override
    @CacheEvict(value = "accounts", allEntries = true)
    public AccountDto updateAccount(AccountDto accountDto) {
        try {

            if (accountDto.getId() == null) {
                throw new SystemException("id.must_be.not_null");
            }

            Account account = accountRepo.findById(accountDto.getId())
                    .orElseThrow(() -> new SystemException("not_found.account"));

            // Update username
            if (accountDto.getUsername() != null
                    && !accountDto.getUsername().equals(account.getUsername())) {

                Optional<Account> existingAccount =
                        accountRepo.findByUsername(accountDto.getUsername());

                if (existingAccount.isPresent()
                        && !existingAccount.get().getId().equals(account.getId())) {

                    throw new SystemException("account.exists");
                }

                account.setUsername(accountDto.getUsername());
            }

            // Update enabled
            if (accountDto.getEnabled() != null) {
                account.setEnabled(accountDto.getEnabled());
            }

            // Update password only if a new password was provided
            if (accountDto.getPassword() != null
                    && !accountDto.getPassword().isBlank()) {

                account.setPassword(
                        passwordEncoder.encode(accountDto.getPassword())
                );
            }

            // Update Account Details
            if (accountDto.getAccountDetails() != null) {

                AccountDetails accountDetails = account.getAccountDetails();

                if (accountDetails == null) {

                    accountDetails = new AccountDetails();
                    accountDetails.setAccount(account);
                    account.setAccountDetails(accountDetails);
                }

                accountDetails.setAge(
                        accountDto.getAccountDetails().getAge()
                );

                accountDetails.setPhoneNumber(
                        accountDto.getAccountDetails().getPhoneNumber()
                );

                accountDetails.setAddress(
                        accountDto.getAccountDetails().getAddress()
                );
            }

            Account updatedAccount = accountRepo.save(account);

            return AccountMapper.ACCOUNT_MAPPER.toAccountDto(updatedAccount);

        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    // =========================
    // VALIDATE UPDATE
    // =========================

    private void validateUpdateAccount(
            Long id)
            throws SystemException {

        if (Objects.isNull(id)) {

            throw new SystemException(
                    "id.must_be.not_null"
            );
        }

        if (Objects.isNull(
                getAccountById(id)
        )) {

            throw new SystemException(
                    "not_found.account"
            );
        }
    }


    // =========================
    // DELETE ACCOUNT
    // =========================

    @Override
    @CacheEvict(value = "accounts", allEntries = true)
    public void deleteAccount(Long id) {
        try {
            validateUpdateAccount(id);
            accountRepo.deleteById(id);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("account.delete.not.allowed");
        }
    }


    // =========================
    // GET ACCOUNT BY ID
    // =========================

    @Override
    @Cacheable(
            value = "accounts",
            key = "'id-' + #id"
    )
    public AccountDto getAccountById(
            Long id) {

        try {

            if (Objects.isNull(id)) {

                throw new SystemException(
                        "id.must_be.not_null"
                );
            }

            Optional<Account> result =
                    accountRepo.findById(id);

            if (result.isEmpty()) {

                throw new SystemException(
                        "not_found.account"
                );
            }

            return AccountMapper.ACCOUNT_MAPPER
                    .toAccountDto(
                            result.get()
                    );

        } catch (SystemException e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }


    // =========================
    // GET ACCOUNT BY USERNAME
    // =========================

    @Override
    @Cacheable(
            value = "accounts",
            key = "'username-' + #username"
    )
    public AccountDto getAccountByUsername(
            String username) {

        try {

            if (username == null
                    || username.trim().isEmpty()) {

                throw new SystemException(
                        "not_empty.name"
                );
            }

            Optional<Account> result =
                    accountRepo.findByUsername(
                            username
                    );

            if (result.isEmpty()) {

                return null;
            }

            return AccountMapper.ACCOUNT_MAPPER
                    .toAccountDto(
                            result.get()
                    );

        } catch (SystemException e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }
}