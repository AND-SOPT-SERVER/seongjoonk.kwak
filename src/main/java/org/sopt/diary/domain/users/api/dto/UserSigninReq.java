package org.sopt.diary.domain.users.api.dto;

public record UserSigninReq(
        String loginId,
        String password
) {
}
