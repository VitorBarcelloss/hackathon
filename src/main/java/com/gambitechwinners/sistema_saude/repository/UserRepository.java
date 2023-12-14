package com.gambitechwinners.sistema_saude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.gambitechwinners.sistema_saude.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}