package com.almoxarifado.controller;

import com.almoxarifado.dao.FerramentaDAO;
import com.almoxarifado.dao.RegistroDAO;
import com.almoxarifado.model.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private RegistroDAO registroDAO;

    @Autowired
    private FerramentaDAO ferramentaDAO;

    @GetMapping
    public List<Registro> listar() {
        return registroDAO.listarTodos();
    }

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Map<String, Object> body) {
        int ferramentaId  = (int) body.get("ferramentaId");
        int funcionarioId = (int) body.get("funcionarioId");
        String tipo       = (String) body.get("tipo"); // "RETIRADA" ou "DEVOLUCAO"

        var ferramenta = ferramentaDAO.buscarPorId(ferramentaId);
        if (ferramenta == null) return ResponseEntity.badRequest().body("Ferramenta não encontrada.");

        if ("RETIRADA".equalsIgnoreCase(tipo)) {
            if (!ferramenta.isDisponivel() || ferramenta.getQuantidade() <= 0)
                return ResponseEntity.badRequest().body("Ferramenta indisponível para retirada.");
            ferramenta.setQuantidade(ferramenta.getQuantidade() - 1);
        } else if ("DEVOLUCAO".equalsIgnoreCase(tipo)) {
            ferramenta.setQuantidade(ferramenta.getQuantidade() + 1);
        } else {
            return ResponseEntity.badRequest().body("Tipo inválido. Use RETIRADA ou DEVOLUCAO.");
        }

        ferramentaDAO.editar(ferramenta);
        registroDAO.inserir(ferramentaId, funcionarioId, tipo.toUpperCase());
        return ResponseEntity.ok("Movimentação registrada.");
    }
}
