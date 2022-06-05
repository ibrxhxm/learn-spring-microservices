package com.example.photoappusers.service;

import com.example.photoappusers.domain.dto.request.CreateUserRequest;
import com.example.photoappusers.domain.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}
