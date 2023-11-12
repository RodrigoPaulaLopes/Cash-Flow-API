package com.rodrigo.cashflowapi.services;

import com.rodrigo.cashflowapi.dtos.AlterarContaDTO;
import com.rodrigo.cashflowapi.dtos.CriarContaDTO;
import com.rodrigo.cashflowapi.dtos.ListarContasDTO;
import com.rodrigo.cashflowapi.dtos.PagarContaDTO;
import com.rodrigo.cashflowapi.entities.Contas;
import com.rodrigo.cashflowapi.entities.Usuarios;
import com.rodrigo.cashflowapi.enums.StatusPagamento;
import com.rodrigo.cashflowapi.exceptions.BusinessException;
import com.rodrigo.cashflowapi.repositories.ContasRepository;
import com.rodrigo.cashflowapi.repositories.UsuariosRepository;
import com.rodrigo.cashflowapi.utils.Calc;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContasService {


    @Autowired
    private ContasRepository contasRepository;

    @Autowired
    private UsuariosService usuariosService;


    public ListarContasDTO create(CriarContaDTO body) {
        try {
            var usuario = usuariosService.currentUser();

            var conta = new Contas(body, usuario);

            var newContas = contasRepository.save(conta);

            return new ListarContasDTO(newContas);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    public ListarContasDTO update(AlterarContaDTO body) {
        try {

            var conta = contasRepository.getReferenceById(body.id());

            if (conta == null) throw new EntityNotFoundException("Conta não encontrada");

            conta.alterar(body);

            var updatedContas = contasRepository.save(conta);

            return new ListarContasDTO(updatedContas);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    public Page<ListarContasDTO> findAll(Pageable paginacao) {
        var usuario = usuariosService.currentUser();
        System.out.println(usuario);
        return contasRepository.findContasByUsuarioId(paginacao, usuario.getId()).map(ListarContasDTO::new);


    }


    public ListarContasDTO pay(PagarContaDTO body) {

        var conta = contasRepository.getReferenceById(body.id());

        if (conta == null) throw new EntityNotFoundException("Conta não existe");

        Contas updatedConta = null;
        if (conta.getParcelas() <= 1) {

            if (body.valor() != conta.getValor_parcela())
                throw new BusinessException("o valor que você esta enviado está incoreto, deveria ser " + conta.getValor_parcela());

            conta.setStatus_pagamento(StatusPagamento.PAGA);
            updatedConta = contasRepository.save(conta);
        }

        if (conta.getParcelas() > 1) {

            if (body.valor() != conta.getValor_parcela())
                throw new BusinessException("o valor que você esta enviado está incoreto, deveria ser " + conta.getValor_parcela());

            conta.setStatus_pagamento(StatusPagamento.PARCIALMENTE_PAGA);
            conta.setParcelas(conta.getParcelas() - 1);
            updatedConta = contasRepository.save(conta);
        }
        return new ListarContasDTO(updatedConta);

    }

    public ListarContasDTO findById(String id) {
        var conta = contasRepository.getReferenceById(id);

        if (conta == null) throw new EntityNotFoundException("Conta não encontrada");

        return new ListarContasDTO(conta);

    }

    public ListarContasDTO findByTitulo(String titulo) {
        var conta = contasRepository.findByTitulo(titulo);

        if (conta == null) throw new EntityNotFoundException("Conta não encontrada");

        return new ListarContasDTO(conta);

    }

    public void delete(String id){
        try{
            var conta = contasRepository.getReferenceById(id);
            contasRepository.delete(conta);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
