package com.duc.bookstore.service;

import com.duc.bookstore.entity.User;
import com.duc.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                user.isEnabled(), // có cho phép truy cập không
                true, //account hết hạn chưa
                true, //password hết hạn chưa
                true, //account bị khóa không
                mapRolesToAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())) // Defaule Role format "ROLE_"
                .collect(Collectors.toList());
    }


}
