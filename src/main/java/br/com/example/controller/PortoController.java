package br.com.example.controller;

import br.com.example.model.PortoVO;
import br.com.example.repository.PortoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class PortoController {

    @Autowired
    private PortoRepository repository;

    @GetMapping("/portosProximos/{codigo}")
    public List<PortoVO> portosProximos(@PathVariable String codigo, @RequestParam(defaultValue = "100") double km) {
        List<PortoVO> result = repository.listarPortosProximos(codigo, km * 1000);
        return result;
    }

    @GetMapping("/portosPorUf/{sigla}")
    public List<PortoVO> portosPorUf(@PathVariable String sigla) {
        List<PortoVO> result = repository.listarPortosPorUf(sigla.toUpperCase());
        return result;
    }
}
