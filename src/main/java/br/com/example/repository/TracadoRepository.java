package br.com.example.repository;

import br.com.example.model.Municipio;
import br.com.example.model.MunicipioVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Distâncias para DESENHAR no mapa. Devolvem a distância (geography, em metros), a menor linha entre os
 * dois lugares (ST_ShortestLine) e as geometrias (polígonos simplificados para a resposta ficar leve).
 * Aqui o SQL é nativo, então os nomes das tabelas precisam ser os do seu banco:
 * br_municipios_2025 e aeroportos_2014.
 */
public interface TracadoRepository extends JpaRepository<Municipio, Integer> {

    @Query(value = "select ma.nm_mun, mb.nm_mun, "
            + "cast(ST_Distance(CAST(ma.geom AS geography), CAST(mb.geom AS geography)) as double precision), "
            + "ST_AsText(ST_ShortestLine(ma.geom, mb.geom)), "
            + "ST_AsText(ST_SimplifyPreserveTopology(ma.geom, 0.005)), ST_AsText(ST_SimplifyPreserveTopology(mb.geom, 0.005)) "
            + "from br_municipios_2025 ma, br_municipios_2025 mb where ma.cd_mun = :a and mb.cd_mun = :b limit 1",
            nativeQuery = true)
    List<Object[]> municipioMunicipio(@Param("a") String a, @Param("b") String b);

    @Query(value = "select m.nm_mun, a.municipio, "
            + "cast(ST_Distance(CAST(m.geom AS geography), CAST(a.geom AS geography)) as double precision), "
            + "ST_AsText(ST_ShortestLine(m.geom, a.geom)), "
            + "ST_AsText(ST_SimplifyPreserveTopology(m.geom, 0.005)), ST_AsText(a.geom) "
            + "from br_municipios_2025 m, aeroportos_2014 a where m.cd_mun = :municipio and a.municdv = :aeroporto limit 1",
            nativeQuery = true)
    List<Object[]> municipioAeroporto(@Param("municipio") String municipio, @Param("aeroporto") String aeroporto);

    // Contorno do município consultado: a página desenha ele por baixo dos resultados, para dar contexto
    @Query(value = "select new br.com.example.model.MunicipioVO(m.codigo, m.nome, m.geometria) from Municipio m where m.codigo = :codigo")
    List<MunicipioVO> municipioPorCodigo(@Param("codigo") String codigo);
}
