package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.models.Todo;
import com.example.demo.repositories.TodoRepository;
import com.example.demo.utilties.Utility;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Todo createTodo(Todo t) {

        Todo todo = Todo.builder()
                .title(t.getTitle())
                .userId(Utility.getUserId())
                .build();
        todoRepository.save(todo);
        return todo;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public void deleteTodo(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);

        if (todo.isPresent()) {
            String currentUserId = Utility.getUserId();
            String todoOwnerUserId = todo.get().getUserId();

            if (currentUserId.equals(todoOwnerUserId)) {
                todoRepository.deleteById(id);
            }
        }
    }

    public String loginUser(LoginRequest loginRequest) throws IOException {
        return Utility.loginUser(loginRequest);
    }
}
