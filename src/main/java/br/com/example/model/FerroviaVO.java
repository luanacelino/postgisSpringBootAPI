package br.com.example.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;

@Data
@AllArgsConstructor
public class FerroviaVO implements Serializable {
    private String codigo;
    private String nome;
    private Geometry geometria;
}
