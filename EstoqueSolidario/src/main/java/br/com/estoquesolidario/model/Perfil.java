package br.com.estoquesolidario.model;

public enum Perfil {
    ADMINISTRADOR("Administrador"),
    DOADOR("Doador"),
    DONATARIO("Donatário");

    private String descricao;

    Perfil(String descricao) {
        this.descricao= descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}