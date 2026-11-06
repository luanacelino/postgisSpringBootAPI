package br.com.example.repository;

import br.com.example.model.Ferrovia;
import br.com.example.model.FerroviaVO;
import br.com.example.model.MunicipioVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FerroviaRepository extends JpaRepository<Ferrovia, Integer> {

    // Consulta 5 - intersects: trechos de ferrovia que passam por um município
    @Query(value = "select new br.com.example.model.FerroviaVO(f.codigoSimples, f.nome, f.geometria) from Ferrovia f, Municipio m where intersects(f.geometria, m.geometria) = true and m.codigo = :codigo")
    List<FerroviaVO> listarFerroviasDoMunicipio(@Param("codigo") String codigo);

    // Consulta 6 - intersects: municípios por onde passa uma ferrovia
    @Query(value = "select new br.com.example.model.MunicipioVO(m.codigo, m.nome, m.geometria) from Municipio m where exists (select 1 from Ferrovia f where intersects(m.geometria, f.geometria) = true and f.nome = :ferrovia)")
    List<MunicipioVO> listarMunicipiosDaFerrovia(@Param("ferrovia") String ferrovia);
}
