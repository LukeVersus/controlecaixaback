package com.omega.controlecaixa.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "usuario", schema = "omega")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", schema = "omega", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Campo 'Nome', obrigatório.")
    private String nome;

    @NotEmpty(message = "Campo 'Usuario', obrigatório.")
    private String username;

    @NotEmpty(message = "Campo 'Password', obrigatório.")
    private String password;

    private boolean ativo = true;

    @Email
    private String email;
}
