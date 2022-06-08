package com.familyevents.account;


import com.familyevents.entity.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

//adapter springsecurity and domain user infomation의 간극을 메워주는 adapter
@Getter
public class UserAccount extends User {

    private Account account;

    public UserAccount(Account account){
        super(account.getNickname(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }
}

