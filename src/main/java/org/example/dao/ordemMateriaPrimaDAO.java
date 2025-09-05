package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemMateriaPrima;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ordemMateriaPrimaDAO {

    public void inserirOrdemMateriaPrima(OrdemMateriaPrima ordemMateriaPrima){
        String query = "INSERT INTO OrdemMateriaPrima(idOrdem, idMateriaPrima, quantidade) VALUES (?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,ordemMateriaPrima.getIdOrdem());
            stmt.setInt(2,ordemMateriaPrima.getIdMateriaPrima());
            stmt.setDouble(3,ordemMateriaPrima.getQuantidade());
            stmt.executeUpdate();

            System.out.println("Matéria-prima associada à ordem com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<OrdemMateriaPrima> buscarOrdemMateriaPrimaPorIdOrdemProducao(int idOrdem){
        String query = "SELECT idOrdem, idMateriaPrima, quantidade FROM OrdemMateriaPrima WHERE idOrdem = ?";
        List<OrdemMateriaPrima> ordensMateriasPrimas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,idOrdem);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int idOrdemNew = rs.getInt("idOrdem");
                int idMateriaPrima = rs.getInt("idMateriaPrima");
                double quantidade = rs.getDouble("quantidade");

                var ordemMateriaPrima = new OrdemMateriaPrima(idOrdemNew, idMateriaPrima, quantidade);
                ordensMateriasPrimas.add(ordemMateriaPrima);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return ordensMateriasPrimas;
    }

}
