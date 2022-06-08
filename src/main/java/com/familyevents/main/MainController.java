package com.familyevents.main;

import com.familyevents.account.CurrentUser;
import com.familyevents.entity.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /* CurrentUser라는 Annotation으로 Account type을 받을 것 Principal을 다이나믹하게
         익명 -> null Handler안에서 null check
        */
    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        if(account != null) {
            model.addAttribute(account);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        // SpringBoot ViewController로 줄일 수 있음
        return "login";
        // ViewResolver에 의해 preifx templates/
        // surfix .html이 붙게 설정되어 있는 것임
    }
}
