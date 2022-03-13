package com.familyevents.account;


import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRepository{
    boolean existsByEmail(String email);// Account class , id
    boolean existsByNickName(String nickname);
}
