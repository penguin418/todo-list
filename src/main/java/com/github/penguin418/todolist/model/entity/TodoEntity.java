package com.github.penguin418.todolist.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.penguin418.todolist.model.constant.TodoState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class TodoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private TodoUserEntity todoUser;

    @Column(name = "todo_content")
    private String todoContent;

    @Column(name = "todo_state")
    private TodoState todoState;

    public void setTodoState(TodoState newState){
        if (!TodoState.isValidTransition(this.todoState, newState)){
            throw new IllegalArgumentException("Invalid state transition");
        }
        this.todoState = newState;
    }
}
