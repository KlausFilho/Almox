package com.almoxarifado.model;

public class Registro {
    private int id;
    private String ferramenta;
    private String funcionario;
    private String tipo;
    private String dataHora;

    public Registro() {}

    public Registro(int id, String ferramenta, String funcionario, String tipo, String dataHora) {
        this.id = id;
        this.ferramenta = ferramenta;
        this.funcionario = funcionario;
        this.tipo = tipo;
        this.dataHora = dataHora;
    }

    public int getId() { return id; }
    public String getFerramenta() { return ferramenta; }
    public String getFuncionario() { return funcionario; }
    public String getTipo() { return tipo; }
    public String getDataHora() { return dataHora; }

    public void setId(int id) { this.id = id; }
    public void setFerramenta(String ferramenta) { this.ferramenta = ferramenta; }
    public void setFuncionario(String funcionario) { this.funcionario = funcionario; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
}
