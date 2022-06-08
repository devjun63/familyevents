package com.familyevents.mapper

import com.familyevents.entity.Account
import com.familyevents.util.QueryUtils
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.jdbc.SQL
import org.springframework.util.StringUtils

class AccountSQL {

    public static final String FIND_ACCOUNT =
            '''
            SELECT * FROM account
            '''

    public static final String COUNT =
            '''
            SELECT COUNT(*) FROM account
            '''

    public String createAccount(@Param("SignUpForm") Account account) {
        return new SQL() {{
            INSERT_INTO("ACCOUNT")
            VALUES("EMAIL, NICKNAME, PASSWORD, EMAILCHECKTOKEN, JOINEDAT, EMAILCHECKTOKENGENERATEDAT",
                    "#{SignUpForm.email}, #{SignUpForm.nickname}, #{SignUpForm.password}, " +
                            "#{SignUpForm.emailCheckToken}, NOW(), #{SignUpForm.emailCheckTokenGeneratedAt}")
        }}
    }
    public String findAccountByEmail(@Param("email") String email) {
        return new SQL() {{
            SELECT("*");
            FROM("account");
            WHERE("email=#{email}")
        }}.toString();
    }

    public String findAccountByNickname(@Param("nickname") String nickname) {
        return new SQL() {{
            SELECT("*");
            FROM("account");
            WHERE("nickname=#{nickname}")
        }}.toString();
    }

    public String completeSignup(@Param("userid") Long userid) {
        return new SQL() {{
            UPDATE("account");
            SET("emailVerified=1")
            SET("joinedAt=NOW()")
            WHERE("id=#{userid}")
        }}.toString();
    }

}
