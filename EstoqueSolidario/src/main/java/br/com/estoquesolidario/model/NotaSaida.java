package br.com.estoquesolidario.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "nota_saida")
public class NotaSaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, name="data_hora", columnDefinition = "DATETIME")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy ="notaSaida", cascade = CascadeType.ALL)
    private List<NotaSaidaItem> itens;

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

    public List<NotaSaidaItem> getItens() {
        return itens;
    }

    public void setItens(List<NotaSaidaItem> itens) {
        this.itens = itens;
    }

    public Integer getTotalItens() {
        this.totalItens = 0;
        if(this.itens != null){
            for (NotaSaidaItem notaSaidaItem : itens){
                totalItens += notaSaidaItem.getQuantidade();
            }
        }
        return totalItens;
    }

    public void setTotalItens(Integer totalItens) {
        this.totalItens = totalItens;
    }
}