package com.familyevents.mapper

import com.familyevents.entity.Account
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.InsertProvider
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectProvider
import org.springframework.stereotype.Repository


@Mapper
@Repository
interface AccountMapper {

    @InsertProvider(type = AccountSQL.class, method = "Insert_Account")
    int insert(@Param("account") Account account);

    @SelectProvider(type = AccountSQL.class, method = "Find_Account")
    public Account findAccount()
}