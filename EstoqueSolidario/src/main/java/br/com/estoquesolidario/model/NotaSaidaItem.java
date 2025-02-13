package br.com.estoquesolidario.model;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="nota_saida_itens")
public class NotaSaidaItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name="nota_saida_id")
    private NotaSaida notaSaida;

    private Integer quantidade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="data_validade", columnDefinition = "DATE")
    private LocalDate dataDeValidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public NotaSaida getNotaSaida() {
        return notaSaida;
    }

    public void setNotaSaida(NotaSaida notaSaida) {
        this.notaSaida = notaSaida;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}
