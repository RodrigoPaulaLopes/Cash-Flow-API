package com.rodrigo.cashflowapi.dtos;

import com.rodrigo.cashflowapi.enums.StatusPagamento;

import java.time.LocalDate;
import java.util.Date;

public record CriarContaDTO(
                            String titulo,
                            String descricao,
                            int parcelas,
                            double valor,
                            Date dataVencimento
                            ) {
}
