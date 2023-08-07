package marlon.leoner.avaliacao.tecnica.resources;

import marlon.leoner.avaliacao.tecnica.domains.dtos.PautaDto;
import marlon.leoner.avaliacao.tecnica.domains.dtos.VoteResultDto;
import marlon.leoner.avaliacao.tecnica.domains.requests.CreatePautaRequest;
import marlon.leoner.avaliacao.tecnica.domains.requests.OpenVoteRequest;
import marlon.leoner.avaliacao.tecnica.domains.requests.VoteRequest;
import marlon.leoner.avaliacao.tecnica.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pauta")
public class PautaResource {

    @Autowired
    private PautaService service;

    @GetMapping("/{slug}")
    public ResponseEntity<?> get(@PathVariable String slug) throws Exception {
        return ResponseEntity.ok(service.get(slug));
    }

    @PostMapping
    public ResponseEntity<PautaDto> create(@RequestBody CreatePautaRequest body) throws Exception {
        service.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{slug}/open")
    public ResponseEntity<PautaDto> open(@PathVariable String slug, @RequestBody(required = false) OpenVoteRequest body) {
        service.open(slug, body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{slug}/vote")
    public ResponseEntity<Void> vote(@PathVariable String slug, @RequestBody VoteRequest body) {
        service.vote(slug, body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{slug}/result")
    public ResponseEntity<VoteResultDto> count(@PathVariable String slug) {
        return ResponseEntity.ok(service.count(slug));
    }
}
