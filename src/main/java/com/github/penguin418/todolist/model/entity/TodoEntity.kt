package com.github.penguin418.todolist.model.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.github.penguin418.todolist.model.constant.TodoState
import com.github.penguin418.todolist.model.constant.TodoState.Companion.isValidTransition
import jakarta.persistence.*
import lombok.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener::class)
class TodoEntity() : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
     var todoId: Long? = null

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
     var todoUser: TodoUserEntity? = null

    @Column(name = "todo_content")
     var todoContent: String? = null

    @Column(name = "todo_state")
     var todoState: TodoState? = null
        get() = field
        set(value){
            require(isValidTransition(field, value!!)) { "Invalid state transition" }
            field = value
        }
}
