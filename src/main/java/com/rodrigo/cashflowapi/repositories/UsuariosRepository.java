package com.rodrigo.cashflowapi.repositories;

import com.rodrigo.cashflowapi.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
    UserDetails findByUsername(String username);
}
