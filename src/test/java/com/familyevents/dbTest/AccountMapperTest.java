package com.familyevents.dbTest;

import com.familyevents.account.AccountService;
import com.familyevents.entity.Account;
import com.familyevents.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
public class AccountMapperTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private SqlSession sqlSession;


    @Test
    public void testConnection() throws Exception {
        try(Connection connection = dataSource.getConnection()) {

        }
    }

    @Test
    public void find_test() throws  Exception{
        List<Account> findAccounts = accountMapper.findAccount();
        System.out.println(findAccounts);

    }
}
