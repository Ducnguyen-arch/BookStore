package com.duc.bookstore.service;

import com.duc.bookstore.entity.User;
import com.duc.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public boolean existByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public boolean deleteById(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }




}
