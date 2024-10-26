package org.sopt.seminar3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoptMemberRepository extends JpaRepository<SoptMemberEntity, Long> {
}
