package marlon.leoner.avaliacao.tecnica.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import marlon.leoner.avaliacao.tecnica.domains.dtos.PautaDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pautas")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String slug;

    private Long timer;

    private Date openedAt;

    private Date closedAt;

    private Date createdAt;

    @OneToMany(mappedBy = "pauta", fetch = FetchType.LAZY)
    private List<Vote> votes;

    public Boolean allowOpen() {
        return Objects.isNull(this.timer);
    }

    public Boolean isOpened() {
        if (Objects.isNull(this.timer)) {
            return false;
        }

        return closedAt.after(new Date());
    }

    public PautaDto toDto() {
        PautaDto dto = new PautaDto();
        dto.setName(this.getName());
        dto.setDescription(this.getDescription());
        dto.setSlug(this.getSlug());
        dto.setCreatedAt(this.getCreatedAt());

        return dto;
    }
}