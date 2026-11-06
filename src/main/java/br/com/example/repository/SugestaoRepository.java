package br.com.example.repository;

import br.com.example.model.Municipio;
import br.com.example.model.SugestaoVO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** Consultas só para o autocompletar da página (não envolvem geometria). */
public interface SugestaoRepository extends JpaRepository<Municipio, Integer> {

    @Query(value = "select new br.com.example.model.SugestaoVO(m.codigo, m.nome, m.sigla) from Municipio m where lower(m.nome) like :q order by m.nome, m.sigla")
    List<SugestaoVO> sugerirMunicipios(@Param("q") String q, Pageable pageable);

    // aeroporto e porto aparecem pelo município onde ficam (a base não tem o nome do aeroporto/porto)
    @Query(value = "select new br.com.example.model.SugestaoVO(a.codigoMunicipio, coalesce(a.nomeAeroporto, concat('Aeroporto de ', a.municipio)), concat(a.municipio, ' - ', a.nomeUf)) from Aeroporto a where lower(a.municipio) like :q or lower(coalesce(a.nomeAeroporto, '')) like :q order by a.municipio")
    List<SugestaoVO> sugerirAeroportos(@Param("q") String q, Pageable pageable);

    @Query(value = "select new br.com.example.model.SugestaoVO(cast(p.codigoMunicipio as string), coalesce(p.nomePorto, concat('Porto de ', p.municipio)), p.nomeUf) from Porto p where lower(p.municipio) like :q or lower(coalesce(p.nomePorto, '')) like :q order by p.municipio")
    List<SugestaoVO> sugerirPortos(@Param("q") String q, Pageable pageable);

    @Query(value = "select distinct f.nome from Ferrovia f where lower(f.nome) like :q order by f.nome")
    List<String> sugerirFerrovias(@Param("q") String q, Pageable pageable);

    // número da BR = 3 primeiros caracteres do código do trecho (ex.: 050BMG9150 -> 050)
    @Query(value = "select distinct substring(r.codigo, 1, 3) from Rodovia r where r.codigo like :q")
    List<String> sugerirRodovias(@Param("q") String q);

    @Query(value = "select distinct r.tipo from Rodovia r where r.tipo is not null and r.tipo <> '' order by r.tipo")
    List<String> listarTiposRodovia();
}
