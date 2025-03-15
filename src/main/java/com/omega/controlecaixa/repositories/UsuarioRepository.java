package com.omega.controlecaixa.repositories;

import com.omega.controlecaixa.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM omega.usuario WHERE ativo = true limit 1", nativeQuery = true)
    Usuario findFirst();

    Optional<Usuario> findByUsername(String username);

}
