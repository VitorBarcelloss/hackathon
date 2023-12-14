package com.gambitechwinners.sistema_saude.model.request;

import com.gambitechwinners.sistema_saude.model.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
    
}
