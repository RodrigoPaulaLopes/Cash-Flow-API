package com.rodrigo.cashflowapi.controllers;

import com.rodrigo.cashflowapi.dtos.AlterarContaDTO;
import com.rodrigo.cashflowapi.dtos.CriarContaDTO;
import com.rodrigo.cashflowapi.dtos.ListarContasDTO;
import com.rodrigo.cashflowapi.dtos.PagarContaDTO;
import com.rodrigo.cashflowapi.services.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/contas")
public class ContasController {

    @Autowired
    private ContasService contasService;

    @PostMapping
    @Transactional
    public ResponseEntity<ListarContasDTO> create(@RequestBody CriarContaDTO body, UriComponentsBuilder builder) {
        var conta = contasService.create(body);
        var uri = builder.path("/api/contas/{id}").buildAndExpand(conta.id()).toUri();

        return ResponseEntity.created(uri).body(conta);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ListarContasDTO> update(@RequestBody AlterarContaDTO body) {
        var conta = contasService.update(body);
        return ResponseEntity.ok().body(conta);
    }

    @PatchMapping()
    @Transactional
    public ResponseEntity<ListarContasDTO> pagarConta(@RequestBody PagarContaDTO body) {
        var conta = contasService.pay(body);
        return ResponseEntity.ok().body(conta);
    }

    @GetMapping
    public ResponseEntity<Page<ListarContasDTO>> findAll(Pageable paginacao) {
        var contas = contasService.findAll(paginacao);
        return ResponseEntity.ok().body(contas);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ListarContasDTO> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(contasService.findById(id));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<ListarContasDTO> findByTitulo(@PathVariable("titulo") String titulo) {
        return ResponseEntity.ok().body(contasService.findByTitulo(titulo));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        contasService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
