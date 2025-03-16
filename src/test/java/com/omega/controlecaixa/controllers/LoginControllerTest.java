package com.omega.controlecaixa.controllers;

import com.omega.controlecaixa.domain.model.JwtRequest;
import com.omega.controlecaixa.domain.model.JwtResponse;
import com.omega.controlecaixa.domain.service.interfaces.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAuthenticationTokenSuccess() throws Exception {
        JwtRequest request = new JwtRequest("username", "password");
        JwtResponse expectedResponse = new JwtResponse("jwt-token");

        when(loginService.authenticate(request)).thenReturn(expectedResponse);

        ResponseEntity<JwtResponse> response = loginController.createAuthenticationToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(loginService, times(1)).authenticate(request);
    }

    @Test
    void createAuthenticationTokenException() throws Exception {
        JwtRequest request = new JwtRequest("invalidUsername", "invalidPassword");
        String exceptionMessage = "Invalid credentials";

        when(loginService.authenticate(request)).thenThrow(new Exception(exceptionMessage));

        Exception exception = assertThrows(Exception.class, () -> {
            loginController.createAuthenticationToken(request);
        });

        assertEquals(exceptionMessage, exception.getMessage());
        verify(loginService, times(1)).authenticate(request);
    }
}