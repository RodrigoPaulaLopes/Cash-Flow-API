package com.rodrigo.cashflowapi.dtos;

import java.util.Date;

public record AlterarContaDTO(

                            String id,
                            String titulo,
                            String descricao,
                            int parcelas,

                            double valor,
                            Date dataVencimento
                            ) {
}
