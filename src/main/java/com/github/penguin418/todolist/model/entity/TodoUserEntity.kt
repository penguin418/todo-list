package com.github.penguin418.todolist.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import lombok.Getter
import org.hibernate.annotations.BatchSize
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import java.time.LocalDateTime

@Entity
@Getter
@EntityListeners(AuditingEntityListener::class)
class TodoUserEntity(
        @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @field:Column(name = "user_id")
        @field:Id
        var userId: Long?,
        @field:Column(name = "user_name")
        var username: String,
        @field:Column(name = "user_password")
        var password: String,
        @field:Column(name = "user_nickname")
        var nickname: String?
) {
    constructor(): this(null, "", "", "")

    @Column(name = "created_datetime")
    @CreatedDate
    var createdDatetime: LocalDateTime? = null

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "todoUser", fetch = FetchType.LAZY)
    @JsonBackReference
    var todos: List<TodoEntity> = ArrayList()
    val authorities: Collection<GrantedAuthority?>
        get() = AuthorityUtils.createAuthorityList("ROLE_USER")
    val isAccountNonExpired: Boolean
        get() = true
    val isAccountNonLocked: Boolean
        get() = true
    val isCredentialsNonExpired: Boolean
        get() = true
    val isEnabled: Boolean
        get() = true
}
