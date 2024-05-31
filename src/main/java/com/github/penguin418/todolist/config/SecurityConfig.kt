package com.github.penguin418.todolist.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
open class SecurityConfig {
    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**")
        }
    }

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
                .authorizeHttpRequests { request: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                    request.requestMatchers("/").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers("/api/v1/auth/join").permitAll()
                            .requestMatchers("/auth/login").permitAll()
                            .anyRequest().authenticated()
                }
                .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
                .formLogin { form: FormLoginConfigurer<HttpSecurity?> ->
                    form
                            .loginPage("/auth/login")
                            .loginProcessingUrl("/auth/login")
                            .permitAll()
                }
                .logout { logout: LogoutConfigurer<HttpSecurity?> -> logout.logoutUrl("/auth/logout") }
        return http.build()
    }
}