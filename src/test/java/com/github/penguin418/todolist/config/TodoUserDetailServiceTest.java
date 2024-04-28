package com.github.penguin418.todolist.config;

import com.github.penguin418.todolist.model.request.JoinRequest;
import com.github.penguin418.todolist.model.request.WithdrawalRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TodoUserDetailServiceTest {
    @Autowired
    TodoUserDetailService todoUserDetailService;
    @Test
    @DisplayName("정상가입")
    void createAccount() {
        // given
        JoinRequest joinRequest = new JoinRequest("user","pass","nick");
        // when
        todoUserDetailService.createAccount(joinRequest);

        // then
        UserDetails todoUser = todoUserDetailService.loadUserByUsername("user");
        Assertions.assertThat(todoUser).isNotNull();
    }
    @Test
    @DisplayName("중복 가입실패")
    void createAccountFailed() {
        // given
        JoinRequest joinRequest = new JoinRequest("user","pass","nick");
        todoUserDetailService.createAccount(joinRequest);

        // when, then
        Assertions.assertThatThrownBy(()->todoUserDetailService.createAccount(joinRequest));
    }

    @Test
    @DisplayName("필수정보 누락 가입실패")
    void createAccountFailed2() {
        // given
        JoinRequest joinRequest = new JoinRequest("user","pass",null);
        todoUserDetailService.createAccount(joinRequest);

        // when, then
        Assertions.assertThatThrownBy(()->todoUserDetailService.createAccount(joinRequest));
    }

    @Test
    @DisplayName("정상탈퇴")
    void deleteAccount() {

        // given
        JoinRequest joinRequest = new JoinRequest("user","pass","nick");
        todoUserDetailService.createAccount(joinRequest);

        // when
        TodoUser todoUser = (TodoUser) todoUserDetailService.loadUserByUsername("user");
        todoUserDetailService.deleteAccount(todoUser, new WithdrawalRequest("pass"));
    }


    @Test
    @DisplayName("잘못된 비밀번호 사용 시 탈퇴실패")
    void deleteAccountBadCredential() {

        // given
        JoinRequest joinRequest = new JoinRequest("user","pass","nick");
        todoUserDetailService.createAccount(joinRequest);

        // when
        TodoUser todoUser = (TodoUser) todoUserDetailService.loadUserByUsername("user");
        // then
        Assertions.assertThatThrownBy(()->todoUserDetailService.deleteAccount(todoUser, new WithdrawalRequest("pass1")));

    }
}