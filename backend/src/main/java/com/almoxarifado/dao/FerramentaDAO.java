package com.almoxarifado.dao;

import com.almoxarifado.model.Ferramenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FerramentaDAO {

    @Autowired
    private Conexao conexao;

    public List<Ferramenta> listarTodas() {
        List<Ferramenta> lista = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, quantidade, disponivel FROM ferramenta";
        try (Connection con = conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Ferramenta(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("disponivel")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ferramentas: " + e.getMessage());
        }
        return lista;
    }

    public Ferramenta buscarPorId(int id) {
        String sql = "SELECT id, nome, descricao, quantidade, disponivel FROM ferramenta WHERE id = ?";
        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Ferramenta(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("disponivel")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ferramenta: " + e.getMessage());
        }
        return null;
    }

    public boolean inserir(Ferramenta f) {
        String sql = "INSERT INTO ferramenta (nome, descricao, quantidade, disponivel) VALUES (?, ?, ?, ?)";
        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getDescricao());
            ps.setInt(3, f.getQuantidade());
            ps.setBoolean(4, f.getQuantidade() > 0);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir ferramenta: " + e.getMessage());
        }
    }

    public boolean editar(Ferramenta f) {
        String sql = "UPDATE ferramenta SET nome=?, descricao=?, quantidade=?, disponivel=? WHERE id=?";
        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getDescricao());
            ps.setInt(3, f.getQuantidade());
            ps.setBoolean(4, f.getQuantidade() > 0);
            ps.setInt(5, f.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar ferramenta: " + e.getMessage());
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM ferramenta WHERE id=?";
        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ferramenta: " + e.getMessage());
        }
    }
}
