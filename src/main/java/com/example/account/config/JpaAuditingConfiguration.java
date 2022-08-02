package com.example.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing // 스프링 어플리케이션 뜰 때, 바로 JpaAuditing을 켜줌
                   // Entity의 CreatedAt 과 UpdatedAt 자동 생성 위한 어노테이션
public class JpaAuditingConfiguration {
}
