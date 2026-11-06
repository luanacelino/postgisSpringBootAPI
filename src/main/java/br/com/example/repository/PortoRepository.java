package br.com.example.repository;

import br.com.example.model.Porto;
import br.com.example.model.PortoVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PortoRepository extends JpaRepository<Porto, Integer> {

    // Consulta 3 - distance: portos a até X metros de um município
    @Query(value = "select new br.com.example.model.PortoVO(cast(p.codigoMunicipio as string), coalesce(p.nomePorto, concat('Porto de ', p.municipio)), p.geometria) from Porto p, Municipio m where m.codigo = :codigo and distance(geography(p.geometria), geography(m.geometria)) <= :metros")
    List<PortoVO> listarPortosProximos(@Param("codigo") String codigo, @Param("metros") double metros);

    // Consulta 4 - contains: portos que estão dentro dos municípios de uma UF
    @Query(value = "select new br.com.example.model.PortoVO(cast(p.codigoMunicipio as string), coalesce(p.nomePorto, concat('Porto de ', p.municipio)), p.geometria) from Municipio m, Porto p where contains(m.geometria, p.geometria) = true and m.sigla = :sigla")
    List<PortoVO> listarPortosPorUf(@Param("sigla") String sigla);
}
