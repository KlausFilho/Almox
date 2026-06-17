package com.almoxarifado.model;

public class Ferramenta {
    private int id;
    private String nome;
    private String descricao;
    private int quantidade;
    private boolean disponivel;

    public Ferramenta() {}

    public Ferramenta(int id, String nome, String descricao, int quantidade, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.disponivel = quantidade > 0;
    }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}
