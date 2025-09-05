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

    public double buscarEstoquePorID(int idMateriaPrima){
        String query = "SELECT estoque FROM MateriaPrima WHERE id = ?";

        double quantidade = 0;

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, idMateriaPrima);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                quantidade = rs.getDouble("estoque");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return quantidade;
    }

    public void atualizaEstoque(int id, double estoque){
        String query = "UPDATE MateriaPrima SET estoque = ? WHERE  id = ?";

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setDouble(1,estoque);
            stmt.setInt(2,id);
            stmt.executeUpdate();

            System.out.println("Estoque da peça atualizado com sucesso!");

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
