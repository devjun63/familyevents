package com.familyevents.account;

import com.familyevents.entity.Account;
import com.familyevents.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor    // final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class AccountService implements UserDetailsService {



    private final AccountRepositoryImpl accountRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    private final int SIGNUPCHECKNUMBER = 1;
    // saveNewAccount 메소드에서 accountMapper의 create_account가 성공적으로 완료됐을시 비교하기 위한 변수

    public List<Account> findMembers() {
        return accountRepository.findAccounts();
    }

    public Account findMembersByEmail(String memberEmail) {
        return accountRepository.findByEmail(memberEmail);
    }

    public Account findMembersByNickName(String memberName) {
        return accountRepository.findByNickname(memberName);
    }

    public Account processNewAccount(SignUpForm signUpForm) {
        Account newAccount = saveNewAccount(signUpForm);
        // newAccount.generateEmailCheckToken(); //UUID
        sendSignUpConfirmEmail(newAccount);
        return newAccount;
    }

    private Account saveNewAccount(@Valid SignUpForm signUpForm) {
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        account.generateEmailCheckToken(); // UUID
        accountRepository.createAccount(account);
        return accountRepository.findByEmail(signUpForm.getEmail());
    }

    public void sendSignUpConfirmEmail(Account newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //Subject -> 제목 설정 | setText -> 본문설정
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("FamilyEvents, 회원 가입 인증");
        mailMessage.setText("/check-email-token?token=" +
                newAccount.getEmailCheckToken() + "&email=" + newAccount.getEmail());
        javaMailSender.send(mailMessage);
    }

    public void login(Account account) {
        // first principal. second password, third role
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public void completeSignUp(Account account) {
        account.completeSignup();
        accountRepository.completeSignup(account);
        login(account);
    }

    public void sendLoginLink(Account account) {
        account.generateEmailCheckToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(account.getEmail());
        mailMessage.setSubject("FamilyEvents, 로그인 링크");
        mailMessage.setText("/login-by-email?token=" + account.getEmailCheckToken() +
                "&email=" + account.getEmail());
        javaMailSender.send(mailMessage);
    }

    @Transactional(readOnly = true)
    // 데이터 변경 X -> check -> 읽기 전용 트랜잭션 사용시 writeLock
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(emailOrNickname);
        if (account == null) {
            account = accountRepository.findByNickname(emailOrNickname);
        }

        if (account == null) {
            throw new UsernameNotFoundException(emailOrNickname);
        }
        return new UserAccount(account);// principal에 해당하는 객체를 넘김
    }

    public long count() {
        return accountRepository.count();
    }
}
