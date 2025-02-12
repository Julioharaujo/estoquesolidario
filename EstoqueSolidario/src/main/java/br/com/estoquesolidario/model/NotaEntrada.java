package br.com.estoquesolidario.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "nota_entrada")
public class NotaEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, name="data_hora", columnDefinition = "DATETIME")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy ="notaEntrada", cascade = CascadeType.ALL)
    private List<NotaEntradaItem> itens;

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

    public Integer getTotalItens() {
        this.totalItens = 0;
        if (this.itens != null) {
            for(NotaEntradaItem notaEntradaItem : itens){
                totalItens += notaEntradaItem.getQuantidade();
            }
        }
        return totalItens;
    }

    public void setTotalItens(Integer totalItens) {
        this.totalItens = totalItens;
    }

    public List<NotaEntradaItem> getItens() {
        return itens;
    }

    public void setItens(List<NotaEntradaItem> itens) {
        this.itens = itens;
    }
}
