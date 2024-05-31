package com.github.penguin418.todolist.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
open class JpaAuditConfig {
    @Bean
    open fun auditorProvider(): AuditorAware<Long> {
        return AuditorAware {
            Optional.of(SecurityContextHolder.getContext())
                    .map { context: SecurityContext -> context.authentication }
                    .filter { obj: Authentication -> obj.isAuthenticated }
                    .map { obj: Authentication -> obj.principal }
                    .filter { principal: Any? -> principal is TodoUser }
                    .map { authentication: Any -> authentication as TodoUser }
                    .map { obj: TodoUser -> obj.userId }
        }
    }
}