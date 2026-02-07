package com.recipe.api.gateway.config;


import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class UserContext {

    private final String rawJwtToken;


    private final String username;


    private final Collection<GrantedAuthority> authorities;

}
