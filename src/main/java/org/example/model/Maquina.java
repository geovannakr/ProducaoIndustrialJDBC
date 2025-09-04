package org.example.model;

public class Maquina {
    private int id;
    private String nome;
    private int idSetor;
    private String status;

    public Maquina(int id, String nome, int idSetor, String status) {
        this.id = id;
        this.nome = nome;
        this.idSetor = idSetor;
        this.status = status;
    }

    public Maquina(String nome, int idSetor, String status) {
        this.nome = nome;
        this.idSetor = idSetor;
        this.status = status;
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

    public int getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
