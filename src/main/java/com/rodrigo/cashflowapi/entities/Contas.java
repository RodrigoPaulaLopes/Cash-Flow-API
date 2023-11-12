package com.rodrigo.cashflowapi.entities;

import com.rodrigo.cashflowapi.dtos.AlterarContaDTO;
import com.rodrigo.cashflowapi.dtos.CriarContaDTO;
import com.rodrigo.cashflowapi.enums.StatusPagamento;
import com.rodrigo.cashflowapi.utils.Calc;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "contas")
@Entity
public class Contas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private int parcelas;

    @Column(nullable = false)
    private double valor_parcela;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento status_pagamento;

    @Column(nullable = true)
    private Date data_pagamento;

    @Column(nullable = true)
    private Date data_vencimento;

    @ManyToOne()
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;


    public Contas(CriarContaDTO body, Usuarios usuario) {
        this.setTitulo(body.titulo());
        this.setDescricao(body.descricao());
        this.setParcelas(body.parcelas());
        this.setValor_parcela(Calc.fixed(body.valor() / body.parcelas()));
        this.setData_vencimento(body.dataVencimento());
        this.setValor(body.valor());
        this.setStatus_pagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);
        this.setUsuario(usuario);

    }

    public void alterar(AlterarContaDTO body) {
        this.setId(body.id());
        this.setTitulo(body.titulo());
        this.setDescricao(body.descricao());
        this.setParcelas(body.parcelas());
        this.setValor_parcela(Calc.fixed(body.valor() / body.parcelas()));
        this.setData_vencimento(body.dataVencimento());
        this.setValor(body.valor());
        this.setStatus_pagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);

    }

    @Override
    public String toString() {
        return "Contas{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", parcelas=" + parcelas +
                ", valor_parcela=" + valor_parcela +
                ", valor=" + valor +
                ", status_pagamento=" + status_pagamento +
                ", data_pagamento=" + data_pagamento +
                ", data_vencimento=" + data_vencimento +
                ", usuario=" + usuario +
                '}';
    }
}
