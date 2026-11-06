package br.com.example.controller;

import br.com.example.model.MunicipioVO;
import br.com.example.model.RodoviaVO;
import br.com.example.repository.RodoviaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class RodoviaController {

    @Autowired
    private RodoviaRepository repository;

    @GetMapping("/rodoviasDoMunicipio/{codigo}")
    public List<RodoviaVO> rodoviasDoMunicipio(@PathVariable String codigo, @RequestParam(defaultValue = "Pavimentada") String tipo) {
        List<RodoviaVO> result = repository.listarRodoviasDoMunicipio(codigo, tipo);
        return result;
    }

    // numero da BR com 3 dígitos, ex.: 050, 230
    @GetMapping("/municipiosDaRodovia/{br}")
    public List<MunicipioVO> municipiosDaRodovia(@PathVariable String br) {
        List<MunicipioVO> result = repository.listarMunicipiosDaRodovia(br + "%");
        return result;
    }
}
