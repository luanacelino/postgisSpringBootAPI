package br.com.example.controller;

import br.com.example.model.FerroviaVO;
import br.com.example.model.MunicipioVO;
import br.com.example.repository.FerroviaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class FerroviaController {

    @Autowired
    private FerroviaRepository repository;

    @GetMapping("/ferroviasDoMunicipio/{codigo}")
    public List<FerroviaVO> ferroviasDoMunicipio(@PathVariable String codigo) {
        List<FerroviaVO> result = repository.listarFerroviasDoMunicipio(codigo);
        return result;
    }

    @GetMapping("/municipiosDaFerrovia/{ferrovia}")
    public List<MunicipioVO> municipiosDaFerrovia(@PathVariable String ferrovia) {
        List<MunicipioVO> result = repository.listarMunicipiosDaFerrovia(ferrovia);
        return result;
    }
}
