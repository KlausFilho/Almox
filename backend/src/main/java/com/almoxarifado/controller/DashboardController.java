package com.almoxarifado.controller;

import com.almoxarifado.dao.FerramentaDAO;
import com.almoxarifado.dao.FuncionarioDAO;
import com.almoxarifado.dao.RegistroDAO;
import com.almoxarifado.model.Ferramenta;
import com.almoxarifado.model.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private FerramentaDAO ferramentaDAO;

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @Autowired
    private RegistroDAO registroDAO;

    @GetMapping
    public Map<String, Object> resumo() {
        List<Ferramenta> ferramentas = ferramentaDAO.listarTodas();
        List<Registro> registros = registroDAO.listarTodos();

        long disponiveis = ferramentas.stream().filter(Ferramenta::isDisponivel).count();
        long emUso       = ferramentas.stream().filter(f -> !f.isDisponivel()).count();
        long totalFuncs  = funcionarioDAO.listarTodos().size();

        // movimentações de hoje
        String hoje = java.time.LocalDate.now().toString();
        long movHoje = registros.stream()
                .filter(r -> r.getDataHora() != null && r.getDataHora().startsWith(hoje))
                .count();

        // últimas 5 movimentações
        List<Registro> ultimas = registros.stream().limit(5).toList();

        Map<String, Object> res = new HashMap<>();
        res.put("ferramentasDisponiveis", disponiveis);
        res.put("ferramentasEmUso", emUso);
        res.put("totalFerramentas", ferramentas.size());
        res.put("totalFuncionarios", totalFuncs);
        res.put("movimentacoesHoje", movHoje);
        res.put("ultimasMovimentacoes", ultimas);
        return res;
    }
}
