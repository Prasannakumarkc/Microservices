package com.prasanna.service;

import com.prasanna.playload.dto.SignupDTO;
import com.prasanna.playload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;


}
