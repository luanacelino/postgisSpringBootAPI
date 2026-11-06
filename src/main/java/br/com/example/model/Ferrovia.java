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
@Table(name = "ferrovias_2014")
public class Ferrovia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    @Column(name = "ferrovia")
    private String nome;
    @Column(name = "operadora")
    private String operadora;
    @Column(name = "codpnvsimp")
    private String codigoSimples;
    @Column(name = "cod_pnv")
    private String codigoPnv;
    @Column(name = "geom")
    private Geometry geometria;
}
