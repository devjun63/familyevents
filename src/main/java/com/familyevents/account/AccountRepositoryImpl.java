package com.familyevents.account;

import com.familyevents.entity.Account;
import com.familyevents.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public boolean existsByEmail(String email) {
        if(Objects.isNull(accountMapper.findAccountByEmail(email))) return false;
        else return true;
    }

    @Override
    public boolean existsByNickName(String nickname) {
        if(Objects.isNull(accountMapper.findAccountByNickname(nickname))) return false;
        else return true;
    }

    @Override
    public Account findByEmail(String email) {
            return accountMapper.findAccountByEmail(email);
    }

    @Override
    public Account findByNickname(String nickname) {
            return accountMapper.findAccountByNickname(nickname);

    }

    @Override
    public List<Account> findAccounts() {
        return accountMapper.findAccount();
    }

    @Override
    public int createAccount(Account account) {
        return accountMapper.createAccount(account);
    }


    public Long count() {
        return accountMapper.count();
    }

    public void completeSignup(Account account) {
        Long userId = account.getId();
        if(1 == accountMapper.completeSignup(userId)){
            System.out.println("done");
        }
        else{
            System.out.println("not done");
        }
    }
}
