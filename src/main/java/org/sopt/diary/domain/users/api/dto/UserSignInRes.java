package org.sopt.diary.domain.users.api.dto;

import org.sopt.diary.domain.users.entity.User;

public record UserSignInRes(
        Long userId
) {
    public static UserSignInRes of(Long userId) {
        return new UserSignInRes(userId);
    }
}
