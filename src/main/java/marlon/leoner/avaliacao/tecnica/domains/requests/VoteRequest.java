package marlon.leoner.avaliacao.tecnica.domains.requests;

import lombok.Data;

@Data
public class VoteRequest {

    private String vote;

    private String user;
}
