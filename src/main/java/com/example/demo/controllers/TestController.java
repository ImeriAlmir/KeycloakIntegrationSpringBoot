package com.example.demo.controllers;

import com.example.demo.dto.LoginRequest;
import com.example.demo.models.Todo;
import com.example.demo.services.TodoService;
import com.example.demo.utilties.Utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TestController {

    private final TodoService todoService;

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) throws IOException {
        return todoService.loginUser(loginRequest);
    }

    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("1234");
    }

    @GetMapping("/all")
    public List<Todo> getAllTodos() {

        return todoService.getAllTodos();
    }

    @DeleteMapping("/delete")
    public void deleteTodo() {
        todoService.deleteTodo(1l);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("id");
        String userEmail = (String) token.getTokenAttributes().get("email");
        return ResponseEntity.ok("Hello Admin \nUser Name : " + userName + "\nUser Email : " + userEmail);
    }

    @GetMapping("/user")
    public Map<String, Object> getUser(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        return token.getTokenAttributes();
    }

    @PostMapping("/create")
    public Todo createTodo(@RequestBody Todo todo) {

        return todoService.createTodo(todo);
    }

}
