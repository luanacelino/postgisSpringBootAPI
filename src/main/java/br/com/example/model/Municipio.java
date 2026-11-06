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
@Table(name = "br_municipios_2025")
public class Municipio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    @Column(name = "cd_mun")
    private String codigo;
    @Column(name = "nm_mun")
    private String nome;
    @Column(name = "sigla_uf")
    private String sigla;
    @Column(name = "area_km2")
    private double areaKm2; 
    @Column(name = "geom")
    private Geometry geometria;
  
}
