package marlon.leoner.avaliacao.tecnica.repositories;

import java.util.Optional;

import marlon.leoner.avaliacao.tecnica.domains.Pauta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PautaRepository extends JpaRepository<Pauta, Integer> {

    Optional<Pauta> findBySlug(String slug);

    @Query(value =
            "SELECT pauta " +
            "FROM Pauta pauta " +
            "JOIN FETCH pauta.votes " +
            "WHERE pauta.slug = :slug"
    )
    Optional<Pauta> findFetchBySlug(@Param("slug") String slug);
}
