package com.almoxarifado.dao;

import com.almoxarifado.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FuncionarioDAO {

    @Autowired
    private Conexao conexao;

    public List<Funcionario> listarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.matricula, f.funcao " +
                     "FROM pessoa p JOIN funcionario f ON p.id = f.id";
        try (Connection con = conexao.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("matricula"),
                        rs.getString("funcao")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    public Funcionario buscarPorId(int id) {
        String sql = "SELECT p.id, p.nome, p.matricula, f.funcao " +
                     "FROM pessoa p JOIN funcionario f ON p.id = f.id WHERE p.id = ?";
        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("matricula"),
                        rs.getString("funcao")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage());
        }
        return null;
    }

    public boolean inserir(Funcionario func) {
        String sqlPessoa = "INSERT INTO pessoa (nome, matricula) VALUES (?, ?)";
        String sqlFunc   = "INSERT INTO funcionario (id, funcao) VALUES (?, ?)";
        try (Connection con = conexao.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement psPessoa = con.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            psPessoa.setString(1, func.getNome());
            psPessoa.setString(2, func.getMatricula());
            psPessoa.executeUpdate();
            ResultSet keys = psPessoa.getGeneratedKeys();
            if (keys.next()) {
                int idGerado = keys.getInt(1);
                PreparedStatement psFunc = con.prepareStatement(sqlFunc);
                psFunc.setInt(1, idGerado);
                psFunc.setString(2, func.getFuncao());
                psFunc.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário: " + e.getMessage());
        }
    }

    public boolean editar(Funcionario func) {
        String sqlPessoa = "UPDATE pessoa SET nome=?, matricula=? WHERE id=?";
        String sqlFunc   = "UPDATE funcionario SET funcao=? WHERE id=?";
        try (Connection con = conexao.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
            psPessoa.setString(1, func.getNome());
            psPessoa.setString(2, func.getMatricula());
            psPessoa.setInt(3, func.getId());
            psPessoa.executeUpdate();
            PreparedStatement psFunc = con.prepareStatement(sqlFunc);
            psFunc.setString(1, func.getFuncao());
            psFunc.setInt(2, func.getId());
            psFunc.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar funcionário: " + e.getMessage());
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM pessoa WHERE id=?";
        try (Connection con = conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário: " + e.getMessage());
        }
    }
}
