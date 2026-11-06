-- postgisSpringBootAPI - AS 10 CONSULTAS GEOGRÁFICAS (SQL equivalente ao HQL dos repositories)
-- Para testar no pgAdmin. Troque os códigos de exemplo pelos que quiser.
-- Exemplos de códigos IBGE: João Pessoa 2507507 | Campina Grande 2504009 | Campinas 3509502 |
--                           São Paulo 3550308 | São Sebastião 3550704
-- Consultas 1 e 2: municípios (exemplo do professor). Consultas 3 a 10: aeroportos, portos, ferrovias e rodovias.

-- Consulta 1 - Municípios vizinhos (ST_Touches) -- HQL: touches(ma.geometria, mb.geometria)
SELECT mb.cd_mun, mb.nm_mun
FROM br_municipios_2025 ma
JOIN br_municipios_2025 mb ON ST_Touches(ma.geom, mb.geom)
WHERE ma.cd_mun = '2504009';                       -- Campina Grande

-- Consulta 2 - Distância entre dois municípios, em metros (ST_Distance em geography)
SELECT ST_Distance(ma.geom::geography, mb.geom::geography) AS metros
FROM br_municipios_2025 ma, br_municipios_2025 mb
WHERE ma.cd_mun = '2504009' AND mb.cd_mun = '2507507';   -- Campina Grande e João Pessoa

-- Consulta 3 - Aeroportos do município (ST_Within; ou cadastrados no município)
SELECT a.municdv, COALESCE(a.nome_aeroporto, a.municipio) AS aeroporto, a.geom
FROM aeroportos_2014 a
JOIN br_municipios_2025 m ON (ST_Within(a.geom, m.geom) OR a.municdv = m.cd_mun)
WHERE m.cd_mun = '3509502';                        -- Campinas

-- Consulta 4 - Distância de um município ao aeroporto de outro (ST_Distance em geography)
SELECT ST_Distance(m.geom::geography, a.geom::geography) AS metros
FROM br_municipios_2025 m, br_municipios_2025 ma, aeroportos_2014 a
WHERE m.cd_mun = '2504009'                         -- município de origem (Campina Grande)
  AND ma.cd_mun = '3509502'                        -- município onde o aeroporto está cadastrado (Campinas)
  AND a.municdv = ma.cd_mun;

-- Consulta 5 - Portos próximos: a até X metros do município (distância usada como filtro)
SELECT p.codmun, COALESCE(p.nome_porto, 'Porto de ' || p.municipio) AS porto,
       round((ST_Distance(p.geom::geography, m.geom::geography) / 1000)::numeric, 1) AS km
FROM portos_2014 p, br_municipios_2025 m
WHERE m.cd_mun = '3550704'                         -- São Sebastião
  AND ST_Distance(p.geom::geography, m.geom::geography) <= 100000   -- 100 km
ORDER BY km;

-- Consulta 6 - Portos por UF (ST_Contains)
SELECT p.codmun, COALESCE(p.nome_porto, 'Porto de ' || p.municipio) AS porto
FROM br_municipios_2025 m
JOIN portos_2014 p ON ST_Contains(m.geom, p.geom)
WHERE m.sigla_uf = 'SP';

-- Consulta 7 - Ferrovias do município (ST_Intersects)
SELECT f.codpnvsimp, f.ferrovia, f.operadora
FROM ferrovias_2014 f
JOIN br_municipios_2025 m ON ST_Intersects(f.geom, m.geom)
WHERE m.cd_mun = '3550308';                        -- São Paulo

-- Consulta 8 - Municípios por onde passa uma ferrovia (ST_Intersects + EXISTS)
--   (veja os nomes válidos com: SELECT DISTINCT ferrovia FROM ferrovias_2014 ORDER BY 1;)
SELECT m.cd_mun, m.nm_mun
FROM br_municipios_2025 m
WHERE EXISTS (SELECT 1 FROM ferrovias_2014 f
              WHERE ST_Intersects(m.geom, f.geom) AND f.ferrovia = 'Ferrovia Norte Sul');

-- Consulta 9 - Rodovias do município, de um tipo (ST_Intersects + filtro de atributo)
SELECT r.codigo, r.descseg
FROM rodovia_2014 r
JOIN br_municipios_2025 m ON ST_Intersects(r.geom, m.geom)
WHERE m.cd_mun = '2507507'                         -- João Pessoa
  AND r.tipopnv = 'Pavimentada';

-- Consulta 10 - Municípios por onde passa uma BR (ST_Intersects + EXISTS + LIKE; o código do trecho começa com a BR)
SELECT m.cd_mun, m.nm_mun
FROM br_municipios_2025 m
WHERE EXISTS (SELECT 1 FROM rodovia_2014 r
              WHERE ST_Intersects(m.geom, r.geom) AND r.codigo LIKE '104%');
