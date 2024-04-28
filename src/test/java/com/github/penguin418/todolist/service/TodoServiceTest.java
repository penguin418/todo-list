package com.github.penguin418.todolist.service;

import com.github.penguin418.todolist.config.TodoUser;
import com.github.penguin418.todolist.config.TodoUserDetailService;
import com.github.penguin418.todolist.mapper.TodoUserMapper;
import com.github.penguin418.todolist.model.constant.TodoState;
import com.github.penguin418.todolist.model.dto.TodoDto;
import com.github.penguin418.todolist.model.request.JoinRequest;
import com.github.penguin418.todolist.model.request.TodoCreateRequest;
import com.github.penguin418.todolist.model.request.TodoStatePatchRequest;
import com.github.penguin418.todolist.model.request.WithdrawalRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Autowired
    TodoUserDetailService todoUserDetailService;
    @BeforeEach
    void setUp() {
        // given
        JoinRequest joinRequest = new JoinRequest("user","pass","nick");
        // when
        todoUserDetailService.createAccount(joinRequest);

        // then
        UserDetails todoUser = todoUserDetailService.loadUserByUsername("user");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(todoUser,null,todoUser.getAuthorities()));
    }
    @AfterEach
    void tearDown() {
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        todoUserDetailService.deleteAccount(user, new WithdrawalRequest("pass"));
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("정상작성")
    void create() {
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "할일");
        todoService.create(request);
    }

    @Test
    @DisplayName("내용 누락시 작성실패")
    void createFail() {
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), null);
        Assertions.assertThatThrownBy(()->todoService.create(request));
    }
    @Test
    @DisplayName("상태변경 성공")
    void patchState() {
        // given
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "null");
        // when
        TodoDto todo = todoService.create(request);
        // then
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.TODO));
    }


    @Test
    @DisplayName("잘못된 상태변경 실패(진행중이 아닌 상태에서 대기로 변경)")
    void patchStateFail1() {
        // given
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "null");
        // when
        TodoDto todo = todoService.create(request);
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.TODO));
        // then
        Assertions.assertThatThrownBy(()->todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.PENDING)));
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(진행중이 아닌 상태에서 대기로 변경)")
    void patchStateFail2() {
        // given
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "null");
        // when
        TodoDto todo = todoService.create(request);
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.COMPLETED));
        // then
        Assertions.assertThatThrownBy(()->todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.PENDING)));
    }

    @Test
    @DisplayName("상태변경 성공(대기 상태에서 어떤 상태로든 변경)")
    void patchStateSuccess1() {
        // given
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "null");
        // when 1
        TodoDto todo = todoService.create(request);
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.IN_PROGRESS));
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.PENDING));
        // then 1
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.IN_PROGRESS));
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(대기 상태에서 어떤 상태로든 변경)")
    void patchStateSuccess2() {
        // given
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "null");
        // when 1
        TodoDto todo = todoService.create(request);
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.IN_PROGRESS));
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.PENDING));
        // then 1
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.COMPLETED));
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(대기 상태에서 어떤 상태로든 변경)")
    void patchStateSuccess3() {
        // given
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "null");
        // when 1
        TodoDto todo = todoService.create(request);
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.IN_PROGRESS));
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.PENDING));
        // then 1
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.PENDING));
    }

    @Test
    @DisplayName("잘못된 상태변경 실패(대기 상태에서 어떤 상태로든 변경)")
    void patchStateSuccess4() {
        // given
        TodoUser user = (TodoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoCreateRequest request = new TodoCreateRequest(user.getUserId(), "null");
        // when 1
        TodoDto todo = todoService.create(request);
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.IN_PROGRESS));
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.PENDING));
        // then 1
        todoService.patchState(new TodoStatePatchRequest(todo.getTodoId(), TodoState.TODO));
    }
}