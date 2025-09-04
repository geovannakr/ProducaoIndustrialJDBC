package org.example.model;

import java.time.LocalDate;

public class OrdemProducao {
    private int id;
    private int idProduto;
    private int idMaquina;
    private Double quantidadeProduzir;
    private LocalDate dataSolicitacao;
    private String status;

    public OrdemProducao(int id, int idProduto, int idMaquina, Double quantidadeProduzir, LocalDate dataSolicitacao, String status) {
        this.id = id;
        this.idProduto = idProduto;
        this.idMaquina = idMaquina;
        this.quantidadeProduzir = quantidadeProduzir;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public OrdemProducao(int idProduto, int idMaquina, Double quantidadeProduzir, LocalDate dataSolicitacao, String status) {
        this.idProduto = idProduto;
        this.idMaquina = idMaquina;
        this.quantidadeProduzir = quantidadeProduzir;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public Double getQuantidadeProduzir() {
        return quantidadeProduzir;
    }

    public void setQuantidadeProduzir(Double quantidadeProduzir) {
        this.quantidadeProduzir = quantidadeProduzir;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
