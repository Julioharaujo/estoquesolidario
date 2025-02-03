package br.com.estoquesolidario.model;

public enum Categoria {

    PERECIVEL("Perecível"),
    NAOPERECIVEL("Não-Perecível");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
