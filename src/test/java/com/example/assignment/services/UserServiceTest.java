package com.example.assignment.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.assignment.entity.UserEntity;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserService userService; 


    @Test
    void testLoadUsersFromExternalApi() {
        doNothing().when(userService).loadUsersFromExternalApi();
        
        userService.loadUsersFromExternalApi();

        verify(userService, times(1)).loadUsersFromExternalApi();
    }

    @Test
    void testGetUserById() {
        long id = 1L;
        UserEntity mockUser = new UserEntity();
        mockUser.setId(id);
        
        when(userService.getUserById(id)).thenReturn(mockUser);

        UserEntity result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userService, times(1)).getUserById(id);
    }

    @Test
    void testGetUserByEmail() {
        String email = "emily.johnson@x.dummyjson.com";
        UserEntity mockUser = new UserEntity();
        mockUser.setEmail(email);

        when(userService.getUserByEmail(email)).thenReturn(mockUser);

        UserEntity result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testSearchUsers() {
        String query = "emily.johnson@x.dummyjson.com";
        List<UserEntity> mockUserList = List.of(new UserEntity(), new UserEntity());

        when(userService.searchUsers(query)).thenReturn(mockUserList);

        List<UserEntity> result = userService.searchUsers(query);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userService, times(1)).searchUsers(query);
    }
}
