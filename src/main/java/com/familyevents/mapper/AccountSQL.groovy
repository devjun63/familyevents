package com.familyevents.mapper

import com.familyevents.entity.Account
import com.familyevents.util.QueryUtils
import org.apache.ibatis.jdbc.SQL
import org.springframework.util.StringUtils

class AccountSQL {
    public String Find_Account(Account account) {
        return new SQL() {{
            SELECT("*");
            FROM("account");
        }}.toString();
    }

    public String Insert_Account(Account account){
        return new SQL() {{
            INSERT_INTO("account");
            VALUES("email", account.email)
            VALUES("nickname", account.nickname)
        }}.toString();
    }

}
