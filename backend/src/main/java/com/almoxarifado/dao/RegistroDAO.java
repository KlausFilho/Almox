package com.almoxarifado.dao;

import com.almoxarifado.model.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistroDAO {

    @Autowired
    private Conexao conexao;

    public List<Registro> listarTodos() {
        List<Registro> lista = new ArrayList<>();
        String sql = "SELECT r.id, f.nome AS ferramenta, p.nome AS funcionario, r.tipo, r.data_hora " +
                     "FROM registro r " +
                     "JOIN ferramenta f ON r.ferramenta_id = f.id " +
                     "JOIN pessoa p ON r.funcionario_id = p.id " +
                     "ORDER BY r.data_hora DESC";
        try (Connection con = conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Registro(
                        rs.getInt("id"),
                        rs.getString("ferramenta"),
                        rs.getString("funcionario"),
                        rs.getString("tipo"),
                        rs.getString("data_hora")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar registros: " + e.getMessage());
        }
        return lista;
    }

    public boolean inserir(int ferramentaId, int funcionarioId, String tipo) {
        String sql = "INSERT INTO registro (ferramenta_id, funcionario_id, tipo) VALUES (?, ?, ?)";
        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ferramentaId);
            ps.setInt(2, funcionarioId);
            ps.setString(3, tipo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir registro: " + e.getMessage());
        }
    }
}
