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
@Table(name = "portos_2014")
public class Porto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;
    @Column(name = "uf")
    private String uf;
    @Column(name = "nome_uf")
    private String nomeUf;
    @Column(name = "nome_regia")
    private String regiao;
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "codmun")
    private Integer codigoMunicipio;
    @Column(name = "sum_total")
    private Double movimentacao;
    @Column(name = "nome_porto")
    private String nomePorto;
    @Column(name = "geom")
    private Geometry geometria;
}
