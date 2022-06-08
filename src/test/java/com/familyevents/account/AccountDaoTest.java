package com.familyevents.account;

import com.familyevents.entity.Account;
import com.familyevents.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@MybatisTest
class AccountDaoTest {


    @Autowired
    AccountMapper accountMapper;

    @Test
    void find_account() {
        List<Account> accounts = accountMapper.findAccount();
        System.out.println(accounts);
    }
}