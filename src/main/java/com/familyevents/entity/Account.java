package com.familyevents.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@Data
public class Account {

    // 로그인

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private Boolean emailVerified; // 인증 여부

    private String emailCheckToken;

    private LocalDateTime joinedAt; // 가입일 YY-MM-DD 오전(오후) HH:MM

    // 프로필

    private String bio; // 자기소개

    private int age;

    private String profileImage;

    //

    private LocalDateTime emailCheckTokenGeneratedAt;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignup() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }
}
