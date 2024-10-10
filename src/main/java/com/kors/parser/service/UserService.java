package com.kors.parser.service;

import com.kors.parser.model.UserEntity;
import com.kors.parser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean existsByLogin(String login){
        return userRepository.existsByLogin(login);
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public void save(UserEntity user){
        userRepository.save(user);
    }
}
