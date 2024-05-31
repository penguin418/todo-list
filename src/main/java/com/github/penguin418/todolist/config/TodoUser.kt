package com.github.penguin418.todolist.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

data class TodoUser(
        val userId: Long,
        @JvmField val username: String,
        @JvmField val password: String,
        val nickname: String,
        @JvmField val createdDatetime: LocalDateTime,
        @JvmField val authorities: Collection<GrantedAuthority>,
        @JvmField val accountNonExpired: Boolean,
        @JvmField val accountNonLocked: Boolean,
        @JvmField val credentialsNonExpired: Boolean,
        @JvmField val enabled: Boolean
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities;
    }

    override fun getPassword(): String {
        return password;
    }

    override fun getUsername(): String {
        return username;
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired;
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked;
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired;
    }

    override fun isEnabled(): Boolean {
        return enabled;
    }
}
