package com.familyevents.account;


import com.familyevents.entity.Account;
import com.familyevents.mapper.AccountMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AccountRepository {
    boolean existsByEmail(String email);    // Account class , id

    boolean existsByNickName(String nickname);

    Account findByEmail(String email);
    Account findByNickname(String nickname);
    List<Account> findAccounts();

    int createAccount(Account account);
}
