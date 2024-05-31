package com.github.penguin418.todolist.config

import com.github.penguin418.todolist.model.request.JoinRequest
import com.github.penguin418.todolist.model.request.WithdrawalRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@Transactional
internal class TodoUserDetailServiceTest {
    @Autowired
    var todoUserDetailService: TodoUserDetailService? = null
    @Test
    @DisplayName("정상가입")
    fun createAccount() {
        // given
        val joinRequest = JoinRequest("user", "pass", "nick")
        // when
        todoUserDetailService!!.createAccount(joinRequest)

        // then
        val todoUser = todoUserDetailService!!.loadUserByUsername("user")
        Assertions.assertThat(todoUser).isNotNull()
    }

    @Test
    @DisplayName("중복 가입실패")
    fun createAccountFailed() {
        // given
        val joinRequest = JoinRequest("user", "pass", "nick")
        todoUserDetailService!!.createAccount(joinRequest)

        // when, then
        Assertions.assertThatThrownBy { todoUserDetailService!!.createAccount(joinRequest) }
    }

    @Test
    @DisplayName("필수정보 누락 가입실패")
    fun createAccountFailed2() {
        // given
        val joinRequest = JoinRequest("user", "pass", null)
        todoUserDetailService!!.createAccount(joinRequest)

        // when, then
        Assertions.assertThatThrownBy { todoUserDetailService!!.createAccount(joinRequest) }
    }

    @Test
    @DisplayName("정상탈퇴")
    fun deleteAccount() {

        // given
        val joinRequest = JoinRequest("user", "pass", "nick")
        todoUserDetailService!!.createAccount(joinRequest)

        // when
        val todoUser = todoUserDetailService!!.loadUserByUsername("user") as TodoUser
        todoUserDetailService!!.deleteAccount(todoUser, WithdrawalRequest("pass"))
    }

    @Test
    @DisplayName("잘못된 비밀번호 사용 시 탈퇴실패")
    fun deleteAccountBadCredential() {

        // given
        val joinRequest = JoinRequest("user", "pass", "nick")
        todoUserDetailService!!.createAccount(joinRequest)

        // when
        val todoUser = todoUserDetailService!!.loadUserByUsername("user") as TodoUser
        // then
        Assertions.assertThatThrownBy { todoUserDetailService!!.deleteAccount(todoUser, WithdrawalRequest("pass1")) }
    }
}