package br.com.estoquesolidario.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "doacao_saida")
public class DoacaoSaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, name="data_hora", columnDefinition = "DATETIME")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy ="doacaoSaida", cascade = CascadeType.ALL)
    private List<DoacaoSaidaItem> itens;

    @Transient
    private Integer totalItens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DoacaoSaidaItem> getItens() {
        return itens;
    }

    public void setItens(List<DoacaoSaidaItem> itens) {
        this.itens = itens;
    }

    public Integer getTotalItens() {
        this.totalItens = 0;
        if(this.itens != null){
            for (DoacaoSaidaItem doacaoSaidaItem : itens){
                totalItens += doacaoSaidaItem.getQuantidade();
            }
        }
        return totalItens;
    }

    public void setTotalItens(Integer totalItens) {
        this.totalItens = totalItens;
    }
}