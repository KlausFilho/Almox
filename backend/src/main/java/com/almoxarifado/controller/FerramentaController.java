package com.almoxarifado.controller;

import com.almoxarifado.dao.FerramentaDAO;
import com.almoxarifado.model.Ferramenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ferramentas")
public class FerramentaController {

    @Autowired
    private FerramentaDAO ferramentaDAO;

    @GetMapping
    public List<Ferramenta> listar() {
        return ferramentaDAO.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ferramenta> buscar(@PathVariable int id) {
        Ferramenta f = ferramentaDAO.buscarPorId(id);
        return f != null ? ResponseEntity.ok(f) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Ferramenta f) {
        ferramentaDAO.inserir(f);
        return ResponseEntity.ok("Ferramenta cadastrada.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable int id, @RequestBody Ferramenta f) {
        f.setId(id);
        ferramentaDAO.editar(f);
        return ResponseEntity.ok("Ferramenta atualizada.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id) {
        ferramentaDAO.deletar(id);
        return ResponseEntity.ok("Ferramenta removida.");
    }
}
