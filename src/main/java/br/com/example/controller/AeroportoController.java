package br.com.example.controller;

import br.com.example.model.AeroportoVO;
import br.com.example.repository.AeroportoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AeroportoController {

    @Autowired
    private AeroportoRepository repository;

    @GetMapping("/aeroportosDoMunicipio/{codigo}")
    public List<AeroportoVO> aeroportosDoMunicipio(@PathVariable String codigo) {
        List<AeroportoVO> result = repository.listarAeroportosDoMunicipio(codigo);
        return result;
    }

    @GetMapping("/distanciaMunicipioAeroporto/{municipio}/{aeroporto}")
    public Double distanciaMunicipioAeroporto(@PathVariable String municipio, @PathVariable String aeroporto) {
        Double result = repository.distanciaMunicipioAeroporto(municipio, aeroporto);
        return result;
    }
}
