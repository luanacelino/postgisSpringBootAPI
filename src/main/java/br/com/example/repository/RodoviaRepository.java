package br.com.example.repository;

import br.com.example.model.MunicipioVO;
import br.com.example.model.Rodovia;
import br.com.example.model.RodoviaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RodoviaRepository extends JpaRepository<Rodovia, Integer> {

    // Consulta 7 - intersects: trechos de rodovia de um tipo (ex.: Pavimentada) que passam por um município
    @Query(value = "select new br.com.example.model.RodoviaVO(r.codigo, r.descricao, r.geometria) from Rodovia r, Municipio m where intersects(r.geometria, m.geometria) = true and m.codigo = :codigo and r.tipo = :tipo")
    List<RodoviaVO> listarRodoviasDoMunicipio(@Param("codigo") String codigo, @Param("tipo") String tipo);

    // Consulta 8 - intersects: municípios cortados por uma BR (o código dos trechos começa com o número da BR, ex.: 230)
    @Query(value = "select new br.com.example.model.MunicipioVO(m.codigo, m.nome, m.geometria) from Municipio m where exists (select 1 from Rodovia r where intersects(m.geometria, r.geometria) = true and r.codigo like :padrao)")
    List<MunicipioVO> listarMunicipiosDaRodovia(@Param("padrao") String padrao);
}
