package org.sopt.diary.domain.users.repository;

import org.sopt.diary.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginIdAndPassword(String loginId, String password);
}
