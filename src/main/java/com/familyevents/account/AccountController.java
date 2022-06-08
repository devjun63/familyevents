package com.familyevents.account;

import com.familyevents.entity.Account;
import com.familyevents.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.zip.ZipEntry;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;

    /*
     아키텍처 디자인에 따라 컨트롤러에서 repository 사용을 금지하기도 하지만
     이 강의에서는 Account와 동일한 도메인으로서 사용한다.
     But Controller나 Service를 Repository 혹은 Domain entity에서 참조하지는 않는다.
     */

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);

        // signUpForm데이터를 받을 때 타입의 camelCase이름을 따라감
        // @Valid SignUpForm signUpForm(mapping)

        /*
        signUpSubmit에
        validate 검증을 한번 더 하는 것을 InitBinder로
        signUpSubmit에들어 왔을때 validate 검사를 하고
        signUpFormValidator.validate(signUpForm, errors);
        if(errors.hasErrors());
            return "account/sign-up";
        }
        */
    }
    private final AccountService accountService;

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors) {
        // 복합 객체 ( 여러 값) -> ModelAttribute로 받아오는데 파라미터로 쓰일때 생략이 가능하다.
        // Data를 컨버젼하거나 binding할때 발생할 수 있는 에러를 받아주는 타입 -> Erros

        if (errors.hasErrors()) {
            //SignUpForm JSR 303 Annotation에 정의한 값에 걸리면
            // Binding Errors errors에 담기고 에러가 있다고 조건문에 걸려서 form으로 돌아감
            return "account/sign-up";
        }
        // TODO 회원 가입 처리
        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);

        return "redirect:/";
    }

    @GetMapping("/account")
    public String find_account_by_email(String email) {
        List<Account> accounts = accountService.findMembers();
        System.out.println(accounts.get(0));
        return "index";

    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentUser Account account, Model model){
        model.addAttribute("email", account.getEmail());
        return "account/check-email";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Account account =  accountService.findMembersByEmail(email);
        //accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if (account == null){
            model.addAttribute("error","wrong email");
            return view;
        }
        if(!account.isValidToken(token)){
            model.addAttribute("error","wrong email");
            return view;
        }
        File file;
        ZipEntry entry = null;

        accountService.completeSignUp(account);
        model.addAttribute("numberOfUser", accountService.count()); // n번째 가입임을 알려주기 위함
        model.addAttribute("nickname", account.getNickname());
        return view;
    }


    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentUser Account account, Model model){
        if(!account.canSendConfirmEmail()) {
            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
            model.addAttribute("email", account.getEmail());
            return "account/check-email";
        }
        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/";
    }


    @GetMapping("/email-login")
    public String emailLoginForm() {
        return "account/email-login";
    }

    @PostMapping("/email-login")
    public String sendEmailLoginLink(String email, Model model, RedirectAttributes attributes) {
        Account account = accountService.findMembersByEmail(email);
        if (account == null) {
            model.addAttribute("error", "유효한 이메일 주소가 아닙니다.");
            return "account/email-login";
        }

        if (!account.canSendConfirmEmail()) {
            model.addAttribute("error", "이메일 로그인은 1시간 뒤에 사용할 수 있습니다.");
            return "account/email-login";
        }

        accountService.sendLoginLink(account);
        attributes.addFlashAttribute("message", "이메일 인증 메일을 발송했습니다.");
        return "redirect:/email-login";
    }

    @GetMapping("/login-by-email")
    public String loginByEmail(String token, String email, Model model) {
        Account account = accountService.findMembersByEmail(email);
        String view = "account/logged-in-by-email";
        if (account == null || !account.isValidToken(token)) {
            model.addAttribute("error", "로그인할 수 없습니다.");
            return view;
        }

        accountService.login(account);
        return view;
    }
/*
    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }

 */
}
