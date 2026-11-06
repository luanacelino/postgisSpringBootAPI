package br.com.example.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;

/** Resultado de uma distância "desenhável": os dois lugares, a menor linha entre eles e a distância. */
@Data
@AllArgsConstructor
public class TracadoVO implements Serializable {
    private String origem;
    private String destino;
    private Double metros;
    private Geometry linha;             // menor linha entre os dois lugares
    private Geometry geometriaOrigem;
    private Geometry geometriaDestino;
}
