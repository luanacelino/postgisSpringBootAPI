package br.com.example.controller;

import br.com.example.model.MunicipioVO;
import br.com.example.model.TracadoVO;
import br.com.example.repository.TracadoRepository;
import br.com.example.util.Wkt;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Versões "desenháveis" das consultas de distância (as originais continuam devolvendo só o número). */
@RestController
@CrossOrigin(origins = "*")
public class TracadoController {

    @Autowired
    private TracadoRepository repository;

    private ResponseEntity<TracadoVO> montar(List<Object[]> linhas) {
        if (linhas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Object[] r = linhas.get(0);
        return ResponseEntity.ok(new TracadoVO((String) r[0], (String) r[1], (Double) r[2],
                Wkt.geometria(r[3]), Wkt.geometria(r[4]), Wkt.geometria(r[5])));
    }

    @GetMapping("/tracadoMunicipios/{a}/{b}")
    public ResponseEntity<TracadoVO> municipioMunicipio(@PathVariable String a, @PathVariable String b) {
        return montar(repository.municipioMunicipio(a, b));
    }

    @GetMapping("/tracadoMunicipioAeroporto/{municipio}/{aeroporto}")
    public ResponseEntity<TracadoVO> municipioAeroporto(@PathVariable String municipio, @PathVariable String aeroporto) {
        return montar(repository.municipioAeroporto(municipio, aeroporto));
    }

    @GetMapping("/contexto/municipio/{codigo}")
    public List<MunicipioVO> contextoMunicipio(@PathVariable("codigo") String codigo) {
        return repository.municipioPorCodigo(codigo);
    }
}
