package com.rodrigo.cashflowapi.dtos;

import com.rodrigo.cashflowapi.entities.Contas;
import com.rodrigo.cashflowapi.enums.StatusPagamento;

import java.util.Date;

public record ListarContasDTO(String id, String titulo, String descricao, int parcelas, double valor_parcelas, double valor, StatusPagamento statusPagamento, Date dataPagamento, Date dataVencimento) {

    public ListarContasDTO(Contas conta){
        this(conta.getId(), conta.getTitulo(), conta.getDescricao(), conta.getParcelas(), conta.getValor_parcela(), conta.getValor(), conta.getStatus_pagamento(), conta.getData_pagamento(), conta.getData_vencimento());
    }
}
