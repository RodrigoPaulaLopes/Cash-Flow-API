package com.rodrigo.cashflowapi.controllers;

import com.rodrigo.cashflowapi.dtos.CriarUsuarioDTO;
import com.rodrigo.cashflowapi.dtos.LoginDTO;
import com.rodrigo.cashflowapi.dtos.TokenDTO;
import com.rodrigo.cashflowapi.dtos.UsuarioDTO;
import com.rodrigo.cashflowapi.entities.Usuarios;
import com.rodrigo.cashflowapi.services.TokenService;
import com.rodrigo.cashflowapi.services.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("api/auth")
@RestController
public class AutenticacaoController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO body) {

        var tokenAuth = new UsernamePasswordAuthenticationToken(body.username(), body.password());
        var user = authenticationManager.authenticate(tokenAuth);

        var token = tokenService.create((Usuarios) user.getPrincipal());

        return ResponseEntity.ok().body(new TokenDTO(token));


    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody CriarUsuarioDTO body, UriComponentsBuilder uri) {

        var senha = new BCryptPasswordEncoder().encode(body.password());

        var usuario = usuariosService.create(body.nome(), body.sobrenome(), body.username(), senha);

        var uril = uri.path("api/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uril).body(usuario);
    }


}
