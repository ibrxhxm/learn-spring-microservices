package com.example.photoappusers.service.impl;

import com.example.photoappusers.domain.dto.request.CreateUserRequest;
import com.example.photoappusers.domain.dto.response.UserResponse;
import com.example.photoappusers.domain.model.User;
import com.example.photoappusers.repository.UserRepository;
import com.example.photoappusers.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }
}
