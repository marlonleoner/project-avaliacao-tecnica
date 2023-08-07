package marlon.leoner.avaliacao.tecnica.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marlon.leoner.avaliacao.tecnica.domains.utils.VoteTypeEnum;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vote_id")
    private Integer id;

    @Column(name = "vote_user")
    private String user;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_value")
    private VoteTypeEnum value;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    public Boolean isYes() {
        return this.value.equals(VoteTypeEnum.YES);
    }

    public Boolean isNo() {
        return this.value.equals(VoteTypeEnum.NO);
    }
}
