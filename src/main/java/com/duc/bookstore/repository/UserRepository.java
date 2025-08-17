package com.duc.bookstore.repository;

import com.duc.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long > {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteById(Long id);

    @Query("select u from User u where u.enabled = true")
    List<User> findUserByEnabled();
}
