package br.com.example.repository;

import br.com.example.model.Aeroporto;
import br.com.example.model.AeroportoVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AeroportoRepository extends JpaRepository<Aeroporto, Integer> {

    // Consulta 1 - within: aeroportos que estão dentro de um município (ou cadastrados nele: o ponto real pode cair no município vizinho)
    @Query(value = "select new br.com.example.model.AeroportoVO(a.codigoMunicipio, coalesce(a.nomeAeroporto, a.municipio), a.geometria) from Aeroporto a, Municipio m where m.codigo = :codigo and (within(a.geometria, m.geometria) = true or a.codigoMunicipio = m.codigo)")
    List<AeroportoVO> listarAeroportosDoMunicipio(@Param("codigo") String codigo);

    // Consulta 2 - distance: distância (em metros) entre um município e o aeroporto de outro município
    @Query(value = "select distance(geography(m.geometria), geography(a.geometria)) from Municipio m, Aeroporto a where m.codigo = :municipio and a.codigoMunicipio = :aeroporto")
    Double distanciaMunicipioAeroporto(@Param("municipio") String municipio, @Param("aeroporto") String aeroporto);
}
