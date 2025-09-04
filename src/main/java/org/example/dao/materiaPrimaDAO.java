package org.example.dao;

import org.example.Conexao;
import org.example.model.MateriaPrima;
import org.example.model.OrdemProducao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class materiaPrimaDAO {

    public boolean buscarMateriaPrima(MateriaPrima materiaPrima){
        String query = "SELECT nome FROM MateriaPrima WHERE nome = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,materiaPrima.getNome());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void inserirMateriaPrima(MateriaPrima materiaPrima){
        String query = "INSERT INTO MateriaPrima(nome, estoque) VALUES(?,?)";

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, materiaPrima.getNome());
            stmt.setDouble(2,materiaPrima.getEstoque());
            stmt.executeUpdate();

            System.out.println("Matéria-Prima cadastrada com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<MateriaPrima> listarMateriasPrimas(){
        String query = "SELECT id, nome, estoque FROM MateriaPrima";
        List<MateriaPrima> materiasprimas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                Double estoque = rs.getDouble("estoque");

                var materiaPrima = new MateriaPrima(id, nome, estoque);
                materiasprimas.add(materiaPrima);

            }
            }catch(SQLException e){
            e.printStackTrace();
        }
        return materiasprimas;
    }
}
