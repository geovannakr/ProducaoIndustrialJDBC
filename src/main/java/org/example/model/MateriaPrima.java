package org.example.model;

public class MateriaPrima {

    private int id;
    private String nome;
    private double estoque;

    public MateriaPrima(int id, String nome, double estoque) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
    }

    public MateriaPrima(String nome, double estoque) {
        this.nome = nome;
        this.estoque = estoque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getEstoque() {
        return estoque;
    }

    public void setEstoque(double estoque) {
        this.estoque = estoque;
    }
}
