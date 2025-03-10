package br.com.estoquesolidario.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="doacao_entrada_itens")
public class DoacaoEntradaItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name="doacao_entrada_id")
    private DoacaoEntrada doacaoEntrada;

    private Integer quantidade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="data_validade",columnDefinition = "DATE")
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

    public DoacaoEntrada getDoacaoEntrada() {
        return doacaoEntrada;
    }

    public void setDoacaoEntrada(DoacaoEntrada doacaoEntrada) {
        this.doacaoEntrada = doacaoEntrada;
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
