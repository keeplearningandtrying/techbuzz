package com.sivalabs.techbuzz.service;

import com.sivalabs.techbuzz.entities.User;
import com.sivalabs.techbuzz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username ) throws UsernameNotFoundException {
        return userRepository.findByUsername( username );
    }

    public User findById( Long id ) throws AccessDeniedException {
        return userRepository.findById( id ).get();
    }

    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }
}
