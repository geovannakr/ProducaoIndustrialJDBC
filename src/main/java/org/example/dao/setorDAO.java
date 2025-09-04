package org.example.dao;

import org.example.Conexao;
import org.example.model.Setor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class setorDAO {

    public boolean buscarSetorPorNome(Setor setor){
        String query = "SELECT nome FROM Setor WHERE nome = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, setor.getNome());
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void inserirSetor(Setor setor){
        String query = "INSERT INTO Setor(nome) VALUES (?)";

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,setor.getNome());
            stmt.executeUpdate();

            System.out.println("Setor cadastrado com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Setor> listarSetores (){
        List<Setor> setores = new ArrayList<>();
        String query = "SELECT id, nome FROM Setor";

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");

                var setor = new Setor(id, nome);
                setores.add(setor);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return setores;
    }
}
