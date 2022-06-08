package com.familyevents.mapper

import com.familyevents.entity.Account
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.InsertProvider
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.annotations.Update
import org.apache.ibatis.annotations.UpdateProvider
import org.apache.ibatis.jdbc.SQL
import org.springframework.stereotype.Repository


@Mapper
@Repository
interface AccountMapper {

    @InsertProvider(type = AccountSQL.class, method = "createAccount")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createAccount(@Param("SignUpForm") Account account);

    // 임시로 전체 Account 가져오는 SQL문 -> 삭제하거나 치환
    @Select(AccountSQL.FIND_ACCOUNT)
    public List<Account> findAccount();

    @SelectProvider(type = AccountSQL.class, method = "findAccountByEmail")
    Account findAccountByEmail(@Param("email")  String email);

    @SelectProvider(type = AccountSQL.class, method = "findAccountByNickname")
    Account findAccountByNickname(@Param("nickname") String nickname);

    @Select(AccountSQL.COUNT)
    long count();

    @UpdateProvider(type = AccountSQL.class, method = "completeSignup")
    int completeSignup(@Param("userid") Long userid);
}