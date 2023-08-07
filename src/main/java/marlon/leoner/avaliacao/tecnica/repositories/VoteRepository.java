package marlon.leoner.avaliacao.tecnica.repositories;

import marlon.leoner.avaliacao.tecnica.domains.Pauta;
import marlon.leoner.avaliacao.tecnica.domains.Vote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByUserAndPauta(String user, Pauta pauta);
}
