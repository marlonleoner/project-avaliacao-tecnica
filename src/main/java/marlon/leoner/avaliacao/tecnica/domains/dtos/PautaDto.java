package marlon.leoner.avaliacao.tecnica.domains.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PautaDto {

    private String name;

    private String description;

    private String slug;

    private Date createdAt;
}
