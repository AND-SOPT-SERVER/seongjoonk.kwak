package org.sopt.diary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA가 엔티티를 감시가능
public class JpaAuditingConfig {
}
