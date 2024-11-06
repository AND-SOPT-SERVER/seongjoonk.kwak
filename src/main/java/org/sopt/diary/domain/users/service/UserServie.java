package org.sopt.diary.domain.users.service;

import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.annotations.NotFound;
import org.sopt.diary.common.Failure.CommonFailureInfo;
import org.sopt.diary.common.Failure.UserFailureInfo;
import org.sopt.diary.domain.users.api.dto.UserSignInRes;
import org.sopt.diary.domain.users.entity.User;
import org.sopt.diary.domain.users.repository.UserRepository;
import org.sopt.diary.exception.BusinessException;
import org.springframework.stereotype.Service;


@Service
public class UserServie {
    private final UserRepository userRepository;

    public UserServie(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public void signup(final String loginId, final String password, final String nickName) {

        // todo: DB단에서 유니크키로 중복여부 검사(loginId & nickName)
        userRepository.save(new User(loginId, password, nickName));
    }

    //로그인
    public UserSignInRes signin(final String loginId, final String password) {
        // loginId로 유저찾기
        final User foundUser = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new BusinessException(UserFailureInfo.USER_NOT_FOUND)
        );
        // 찾은 유저 비밀번호와 받은 비밀번호 비교
        if(foundUser.getPassword().equals(password)) {
           return UserSignInRes.of(foundUser.getId());
        } else {
            throw new BusinessException(UserFailureInfo.INVALID_USER_PASSWROD);
        }
    }
}
