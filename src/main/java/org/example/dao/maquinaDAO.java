package org.example.dao;

import org.example.Conexao;
import org.example.model.Maquina;
import org.example.model.Setor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class maquinaDAO {
    public void inserirMaquina(Maquina maquina){
        String query = "INSERT INTO Maquina(nome, idSetor, status) VALUES (?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,maquina.getNome());
            stmt.setInt(2,maquina.getIdSetor());
            stmt.setString(3,maquina.getStatus());
            stmt.executeUpdate();

            System.out.println("Máquina cadastrada com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarMaquinaPorNome(Maquina maquina){
        String query = "SELECT nome FROM Maquina WHERE nome = ? AND idSetor = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setInt(2,maquina.getIdSetor());
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Maquina> listarMaquinas(){
        String query = "SELECT id, nome, idSetor, status FROM Maquina WHERE status = 'OPERACIONAL'";
        List<Maquina> maquinas = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idSetor = rs.getInt("idSetor");
                String status = rs.getString("status");

                var maquina = new Maquina(id, nome, idSetor, status);
                maquinas.add(maquina);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return maquinas;
    }

    public void atualizaStatus(int idMaquina, String status){
        String query = "UPDATE Maquina SET status = ? WHERE id = ?";

        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,status);
            stmt.setInt(2,idMaquina);
            stmt.executeUpdate();

            System.out.println("Status da máquina atualizado para " + status);


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
