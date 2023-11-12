package com.rodrigo.cashflowapi.services;

import com.rodrigo.cashflowapi.dtos.CriarUsuarioDTO;
import com.rodrigo.cashflowapi.dtos.UsuarioDTO;
import com.rodrigo.cashflowapi.entities.Usuarios;
import com.rodrigo.cashflowapi.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService implements UserDetailsService {


    @Autowired
    private UsuariosRepository usuariosRepository;


    public UsuarioDTO create(String nome, String sobrenome, String username, String password){
        try {
            var usuario = new Usuarios(nome, sobrenome, username, password);
            var novo_usuario = usuariosRepository.save(usuario);

            return new UsuarioDTO(novo_usuario);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
    protected Usuarios currentUser(){
        return (Usuarios) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuariosRepository.findByUsername(username);
    }
}
