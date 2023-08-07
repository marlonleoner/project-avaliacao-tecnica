package marlon.leoner.avaliacao.tecnica.domains.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import marlon.leoner.avaliacao.tecnica.domains.Pauta;
import marlon.leoner.avaliacao.tecnica.domains.Vote;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
public class VoteResultDto {

    private Long totalVotes;

    private Long yesVotes;

    private Double percentageYesVotes;

    private Long noVotes;

    private Double percentageNoVotes;

    public VoteResultDto(Pauta pauta) {
        List<Vote> votes = pauta.getVotes();
        this.totalVotes = (long) votes.size();
        this.yesVotes = votes.stream().filter(Vote::isYes).count();
        this.noVotes = votes.stream().filter(Vote::isNo).count();
        double pYesVotes = (this.yesVotes * 100.0d) / totalVotes;
        double pNoVotes = (this.noVotes  * 100.0d) / totalVotes;
        this.percentageYesVotes = new BigDecimal(pYesVotes).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.percentageNoVotes = new BigDecimal(pNoVotes).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
