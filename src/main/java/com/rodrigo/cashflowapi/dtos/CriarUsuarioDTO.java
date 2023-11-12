package com.rodrigo.cashflowapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CriarUsuarioDTO(@NotBlank(message = "nome do usuario deve ser inserido")
                              @NotNull(message = "Nome do usuario não deve ser null")
                              String nome,
                              @NotBlank(message = "Sobrenome do usuario deve ser inserido")
                              @NotNull(message = "Sobrenome do usuario não deve ser null")
                              String sobrenome,

                              @NotBlank(message = "username do usuario deve ser inserido")
                              @NotNull(message = "username do usuario não deve ser null")
                              String username,

                              @NotBlank(message = "Senha do usuario deve ser inserido")
                              @NotNull(message = "Senha do usuario não deve ser null")
                              @Length(min = 8, max = 16, message = "A senha do usuario deve estar entre 8 e 16 caracteres")
                              String password) {
}
