package com.gambitechwinners.sistema_saude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.gambitechwinners.sistema_saude.errors.exception.ResourceBadRequestException;
import com.gambitechwinners.sistema_saude.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

    @Autowired
    UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) {     
        if(repository.findByLogin(username) == null ){
            throw new ResourceBadRequestException("Usuario não encontrado!");
        }
        return repository.findByLogin(username);
    }
    
}