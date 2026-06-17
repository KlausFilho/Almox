package com.almoxarifado.model;

public class Funcionario {
    private int id;
    private String nome;
    private String matricula;
    private String funcao;

    public Funcionario() {}

    public Funcionario(int id, String nome, String matricula, String funcao) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.funcao = funcao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }
}
