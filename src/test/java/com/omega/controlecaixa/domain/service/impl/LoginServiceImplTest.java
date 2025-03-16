package com.omega.controlecaixa.domain.service.impl;

import com.omega.controlecaixa.domain.model.JwtRequest;
import com.omega.controlecaixa.domain.model.JwtResponse;
import com.omega.controlecaixa.support.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(loginService, "authenticationManager", authenticationManager);
        ReflectionTestUtils.setField(loginService, "userDetailsService", userDetailsService);
        ReflectionTestUtils.setField(loginService, "jwtUtil", jwtUtil);
    }

    @Test
    void authenticateSuccess() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("testUser");
        jwtRequest.setPassword("testPassword");

        UserDetails userDetails = mock(UserDetails.class);
        String expectedToken = "test.jwt.token";

        AuthenticationManager authManager = mock(AuthenticationManager.class);
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        ReflectionTestUtils.setField(loginService, "authenticationManager", authManager);
        ReflectionTestUtils.setField(loginService, "userDetailsService", userDetailsService);
        ReflectionTestUtils.setField(loginService, "jwtUtil", jwtUtil);

        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(expectedToken);

        JwtResponse response = loginService.authenticate(jwtRequest);

        verify(authManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );
        verify(userDetailsService).loadUserByUsername("testUser");
        verify(jwtUtil).generateToken(userDetails);

        assertNotNull(response);
        assertEquals(expectedToken, response.getJwtToken());
    }

    @Test
    void authenticateException() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("invalidUser");
        jwtRequest.setPassword("wrongPassword");

        AuthenticationManager authManager = mock(AuthenticationManager.class);
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        ReflectionTestUtils.setField(loginService, "authenticationManager", authManager);
        ReflectionTestUtils.setField(loginService, "userDetailsService", userDetailsService);
        ReflectionTestUtils.setField(loginService, "jwtUtil", jwtUtil);

        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class, () -> {
            loginService.authenticate(jwtRequest);
        });

        verify(authManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtUtil, never()).generateToken(any());
    }
}