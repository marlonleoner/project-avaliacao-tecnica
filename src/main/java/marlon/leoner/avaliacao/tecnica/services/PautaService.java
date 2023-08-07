package marlon.leoner.avaliacao.tecnica.services;

import marlon.leoner.avaliacao.tecnica.domains.Pauta;
import marlon.leoner.avaliacao.tecnica.domains.Vote;
import marlon.leoner.avaliacao.tecnica.domains.dtos.PautaDto;
import marlon.leoner.avaliacao.tecnica.domains.dtos.VoteResultDto;
import marlon.leoner.avaliacao.tecnica.domains.requests.CreatePautaRequest;
import marlon.leoner.avaliacao.tecnica.domains.requests.OpenVoteRequest;
import marlon.leoner.avaliacao.tecnica.domains.requests.VoteRequest;
import marlon.leoner.avaliacao.tecnica.domains.utils.VoteTypeEnum;
import marlon.leoner.avaliacao.tecnica.exceptions.ErrorVoteSessionException;
import marlon.leoner.avaliacao.tecnica.exceptions.InvalidParameterException;
import marlon.leoner.avaliacao.tecnica.exceptions.ObjectAlreadyExistsException;
import marlon.leoner.avaliacao.tecnica.exceptions.ObjectNotFoundException;
import marlon.leoner.avaliacao.tecnica.repositories.PautaRepository;
import marlon.leoner.avaliacao.tecnica.repositories.VoteRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class PautaService {

    private static final String EMPTY_PARAMETER = "O parâmetro slug deve ser preenchido";
    private static final String PAUTA_NOT_FOUND = "Pauta não encontrada";
    private static final String PAUTA_NAME_ALREADY_USED = "Já existe uma pauta com este nome.";
    private static final String USER_ALREADY_VOTE = "O associado já votou nesta pauta.";
    private static final String ERROR_VOTE_OPENED = "Ops! A votação para esta pauta ainda está aberta.";
    private static final String ERROR_VOTE_CLOSED = "Ops! A votação para esta pauta já foi encerrada.";
    private static final String ERROR_OPEN_VOTE = "Ops! A votação para esta pauta já está aberta ou já foi finalizada";


    @Autowired
    private PautaRepository repository;

    @Autowired
    private VoteRepository voteRepository;

    public PautaDto get(String slug) {
        if (StringUtils.isBlank(slug)) {
            throw new InvalidParameterException(EMPTY_PARAMETER);
        }

        Optional<Pauta> pauta = repository.findBySlug(slug);
        if (pauta.isEmpty()) {
            throw new ObjectNotFoundException(PAUTA_NOT_FOUND);
        }

        return pauta.get().toDto();
    }

    public void create(CreatePautaRequest body) {
        Optional<Pauta> pautaExists = repository.findBySlug(body.getSlug());
        if (pautaExists.isPresent()) {
            throw new ObjectAlreadyExistsException(PAUTA_NAME_ALREADY_USED);
        }

        Pauta pauta = body.converter();
        repository.save(pauta);
    }

    public void open(String slug, OpenVoteRequest body) {
        Optional<Pauta> pautaExists = repository.findBySlug(slug);
        if (pautaExists.isEmpty()) {
            throw new ObjectNotFoundException(PAUTA_NOT_FOUND);
        }

        Pauta pauta = pautaExists.get();
        if (!pauta.allowOpen()) {
            throw new ErrorVoteSessionException(ERROR_OPEN_VOTE);
        }

        long timer = Objects.isNull(body) ? 60 : body.getTimer();
        Date now = new Date();
        Date limit = SerializationUtils.clone(now);
        limit.setTime(limit.getTime() + (timer * 1000));

        pauta.setTimer(timer);
        pauta.setOpenedAt(now);
        pauta.setClosedAt(limit);
        repository.save(pauta);
    }

    public void vote(String slug, VoteRequest body) {
        Optional<Pauta> pautaExists = repository.findBySlug(slug);
        if (pautaExists.isEmpty()) {
            throw new ObjectNotFoundException(PAUTA_NOT_FOUND);
        }

        Pauta pauta = pautaExists.get();
        if (!pauta.isOpened()) {
            throw new ErrorVoteSessionException(ERROR_VOTE_CLOSED);
        }

        Optional<Vote> voteExists = voteRepository.findByUserAndPauta(body.getUser(), pauta);
        if (voteExists.isPresent()) {
            throw new ErrorVoteSessionException(USER_ALREADY_VOTE);
        }

        Vote vote = new Vote();
        vote.setValue(VoteTypeEnum.getInstance(body.getVote()));
        vote.setUser(body.getUser());
        vote.setPauta(pauta);
        voteRepository.save(vote);
    }

    public VoteResultDto count(String slug) {
        Optional<Pauta> pautaExists = repository.findFetchBySlug(slug);
        if (pautaExists.isEmpty()) {
            throw new ObjectNotFoundException(PAUTA_NOT_FOUND);
        }

        Pauta pauta = pautaExists.get();
        if (pauta.isOpened()) {
            throw new ErrorVoteSessionException(ERROR_VOTE_OPENED);
        }

        return new VoteResultDto(pauta);
    }
}
