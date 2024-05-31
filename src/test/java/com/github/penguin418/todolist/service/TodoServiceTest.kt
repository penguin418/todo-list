package com.github.penguin418.todolist.service

import com.github.penguin418.todolist.config.TodoUser
import com.github.penguin418.todolist.config.TodoUserDetailService
import com.github.penguin418.todolist.model.constant.TodoState
import com.github.penguin418.todolist.model.request.JoinRequest
import com.github.penguin418.todolist.model.request.TodoCreateRequest
import com.github.penguin418.todolist.model.request.TodoStatePatchRequest
import com.github.penguin418.todolist.model.request.WithdrawalRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@Transactional
internal class TodoServiceTest {
    @Autowired
    var todoService: TodoService? = null

    @Autowired
    var todoUserDetailService: TodoUserDetailService? = null
    @BeforeEach
    fun setUp() {
        // given
        val joinRequest = JoinRequest("user", "pass", "nick")
        // when
        todoUserDetailService!!.createAccount(joinRequest)

        // then
        val todoUser = todoUserDetailService!!.loadUserByUsername("user")
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(todoUser, null, todoUser.authorities)
    }

    @AfterEach
    fun tearDown() {
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        todoUserDetailService!!.deleteAccount(user, WithdrawalRequest("pass"))
        SecurityContextHolder.clearContext()
    }

    @Test
    @DisplayName("정상작성")
    fun create() {
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "할일")
        todoService!!.create(request)
    }

    @Test
    @DisplayName("내용 누락시 작성실패")
    fun createFail() {
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, null)
        Assertions.assertThatThrownBy { todoService!!.create(request) }
    }

    @Test
    @DisplayName("상태변경 성공")
    fun patchState() {
        // given
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "null")
        // when
        val todo = todoService!!.create(request)
        // then
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.TODO))
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(진행중이 아닌 상태에서 대기로 변경)")
    fun patchStateFail1() {
        // given
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "null")
        // when
        val todo = todoService!!.create(request)
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.TODO))
        // then
        Assertions.assertThatThrownBy { todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.PENDING)) }
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(진행중이 아닌 상태에서 대기로 변경)")
    fun patchStateFail2() {
        // given
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "null")
        // when
        val todo = todoService!!.create(request)
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.COMPLETED))
        // then
        Assertions.assertThatThrownBy { todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.PENDING)) }
    }

    @Test
    @DisplayName("상태변경 성공(대기 상태에서 어떤 상태로든 변경)")
    fun patchStateSuccess1() {
        // given
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "null")
        // when 1
        val todo = todoService!!.create(request)
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.IN_PROGRESS))
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.PENDING))
        // then 1
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.IN_PROGRESS))
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(대기 상태에서 어떤 상태로든 변경)")
    fun patchStateSuccess2() {
        // given
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "null")
        // when 1
        val todo = todoService!!.create(request)
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.IN_PROGRESS))
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.PENDING))
        // then 1
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.COMPLETED))
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(대기 상태에서 어떤 상태로든 변경)")
    fun patchStateSuccess3() {
        // given
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "null")
        // when 1
        val todo = todoService!!.create(request)
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.IN_PROGRESS))
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.PENDING))
        // then 1
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.PENDING))
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(대기 상태에서 어떤 상태로든 변경)")
    fun patchStateSuccess4() {
        // given
        val user = SecurityContextHolder.getContext().authentication.principal as TodoUser
        val request = TodoCreateRequest(user.userId, "null")
        // when 1
        val todo = todoService!!.create(request)
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.IN_PROGRESS))
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.PENDING))
        // then 1
        todoService!!.patchState(TodoStatePatchRequest(todo.todoId, TodoState.TODO))
    }
}