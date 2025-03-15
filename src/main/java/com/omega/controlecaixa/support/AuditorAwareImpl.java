package com.omega.controlecaixa.support;

import com.omega.controlecaixa.domain.model.Usuario;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            Object usuario = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (usuario instanceof UserDetails) {
                return Optional.of(((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getNome());
            }
        }
        return Optional.of("");
    }
}
