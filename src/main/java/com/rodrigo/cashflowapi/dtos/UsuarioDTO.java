package com.rodrigo.cashflowapi.dtos;

import com.rodrigo.cashflowapi.entities.Usuarios;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UsuarioDTO(String id,
                         String nome,

                         String sobrenome,


                         String username,

                         String password) {
    public UsuarioDTO(Usuarios novoUsuario) {
        this(novoUsuario.getId(), novoUsuario.getNome(), novoUsuario.getSobrenome(), novoUsuario.getUsername(), novoUsuario.getPassword());
    }
}
