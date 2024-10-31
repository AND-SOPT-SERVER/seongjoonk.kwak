package org.sopt.diary.domain.users.api.dto;

public record UserSignUpReq(
        String loginId,
        String password,
        String nickname
) {
}
