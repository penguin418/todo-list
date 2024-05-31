package com.github.penguin418.todolist.model.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedBy
    @Column(name = "created_by")
    var createdBy: Long? = null

    @CreatedDate
    @Column(name = "created_datetime")
    var createdDatetime: LocalDateTime? = null

    @LastModifiedBy
    @Column(name = "modified_by")
    var modifiedBy: Long? = null

    @LastModifiedDate
    @Column(name = "modified_datetime")
    var modifiedDatetime: LocalDateTime? = null
}
