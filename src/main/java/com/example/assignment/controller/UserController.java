package com.example.assignment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.entity.UserEntity;
import com.example.assignment.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping(value = "/load", produces = "application/json")
    @Operation(summary = "Load users from external API")
    public ResponseEntity<Map<String, String>> loadUsers() {
        userService.loadUsersFromExternalApi();
        Map<String, String> response = new HashMap<>();
        response.put("message", "The user data has been loaded");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/user", produces = "application/json")
    @Operation(summary = "Get all the users from the DataBase")
    public ResponseEntity<List<UserEntity>> getUserById() {
        return ResponseEntity.ok(userService.getAllUser());
    }
    
    @GetMapping(value = "/search", produces = "application/json")
    @Operation(summary = "Search users by firstName, lastName, or SSN")
    public ResponseEntity<List<UserEntity>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @GetMapping(value = "/email/{email}", produces = "application/json")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable @Email String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}
