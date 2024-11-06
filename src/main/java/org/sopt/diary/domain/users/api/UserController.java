package org.sopt.diary.domain.users.api;

import org.sopt.diary.domain.users.api.dto.UserSignInRes;
import org.sopt.diary.domain.users.api.dto.UserSignUpReq;
import org.sopt.diary.domain.users.api.dto.UserSigninReq;
import org.sopt.diary.domain.users.service.UserServie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserServie userServie;

    public UserController(UserServie userServie) {
        this.userServie = userServie;
    }

    @PostMapping("/users/signup")
    public ResponseEntity<Void> signup(@RequestBody final UserSignUpReq userSignUpReq) {
        userServie.signup(userSignUpReq.loginId(), userSignUpReq.password(), userSignUpReq.nickname());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/signin")
    public ResponseEntity<UserSignInRes> signin(@RequestBody final UserSigninReq userSigninReq) {
        final UserSignInRes userSignInRes = userServie.signin(userSigninReq.loginId(), userSigninReq.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(userSignInRes);
    }


}
