package com.rodrigo.cashflowapi.repositories;

import com.rodrigo.cashflowapi.entities.Contas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContasRepository extends JpaRepository<Contas, String> {
    Page<Contas> findContasByUsuarioId(Pageable paginacao, String id);

    Contas findByTitulo(String titulo);
}
