package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemProducao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ordemProducaoDAO {

    public void inserirOrdemProducao(OrdemProducao ordemProducao){
        String query = "INSERT INTO OrdemProducao(idProduto, idMaquina, quantidadeProduzir, dataSolicitacao, status) VALUES (?,?,?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,ordemProducao.getIdProduto());
            stmt.setInt(2,ordemProducao.getIdMaquina());
            stmt.setDouble(3,ordemProducao.getQuantidadeProduzir());
            stmt.setDate(4, Date.valueOf(ordemProducao.getDataSolicitacao()));
            stmt.setString(5,ordemProducao.getStatus());
            stmt.executeUpdate();

            System.out.println("Ordem de Produção criada com sucesso!");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<OrdemProducao> listarOrdensDeProducao(){
        String query = "SELECT id, idPrduto, idMaquina, quantidadeProduzir, dataSolicitacao, status FROM OrdemProducao WHERE status = 'PENDENTE'";
        List<OrdemProducao> ordens = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                int idProduto = rs.getInt("idProduto");
                int idMaquina = rs.getInt("idMaquina");
                Double quantidadeProduzir = rs.getDouble("quantidadeProduzir");
                LocalDate dataSolicitacao = rs.getDate("dataSolicitacao").toLocalDate();
                String status = rs.getString("status");

                var ordemProducao = new OrdemProducao(id, idProduto, idMaquina, quantidadeProduzir, dataSolicitacao, status);
                ordens.add(ordemProducao);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return ordens;
    }
}
