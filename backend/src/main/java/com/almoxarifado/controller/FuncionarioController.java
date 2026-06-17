package com.almoxarifado.controller;

import com.almoxarifado.dao.FuncionarioDAO;
import com.almoxarifado.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @GetMapping
    public List<Funcionario> listar() {
        return funcionarioDAO.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscar(@PathVariable int id) {
        Funcionario f = funcionarioDAO.buscarPorId(id);
        return f != null ? ResponseEntity.ok(f) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Funcionario f) {
        funcionarioDAO.inserir(f);
        return ResponseEntity.ok("Funcionário cadastrado.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable int id, @RequestBody Funcionario f) {
        f.setId(id);
        funcionarioDAO.editar(f);
        return ResponseEntity.ok("Funcionário atualizado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id) {
        funcionarioDAO.deletar(id);
        return ResponseEntity.ok("Funcionário removido.");
    }
}
