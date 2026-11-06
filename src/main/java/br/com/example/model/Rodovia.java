package br.com.example.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;

@Data
@Entity
@Table(name = "rodovia_2014")
public class Rodovia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    @Column(name = "descseg")
    private String descricao;
    @Column(name = "tipopnv")
    private String tipo;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "geom")
    private Geometry geometria;
}
