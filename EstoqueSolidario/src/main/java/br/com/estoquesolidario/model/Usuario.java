package br.com.estoquesolidario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    @NotBlank(message="informe o nome")
    @Size(min = 3, max = 50)
    private String nome;

    @Column(length =14)
    @CPF(message = "CPF inválido")
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="data_nascimento",columnDefinition = "DATE")
    @NotNull(message = "Informe a data de nascimento")
    private LocalDate dataDeNascimento;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Informe o sexo")
    private Sexo sexo;

    @Column(length = 14)
    private String telefone;

    @Column(length = 15)
    private String celular;

    @Column(length =50)
    private String email;

    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public Usuario() {
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
