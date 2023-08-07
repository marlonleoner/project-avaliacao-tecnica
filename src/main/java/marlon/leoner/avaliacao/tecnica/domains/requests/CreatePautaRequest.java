package marlon.leoner.avaliacao.tecnica.domains.requests;

import lombok.Data;

import marlon.leoner.avaliacao.tecnica.domains.Pauta;

import java.text.Normalizer;
import java.util.Date;

@Data
public class CreatePautaRequest {

    private String name;

    private String description;

    public String getSlug() {
        String normalizedName = Normalizer.normalize(this.name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return normalizedName.toLowerCase().replace(" ", "-");
    }
    public Pauta converter() {
        Pauta pauta = new Pauta();
        pauta.setName(this.getName());
        pauta.setDescription(this.getDescription());
        pauta.setSlug(this.getSlug());
        pauta.setCreatedAt(new Date());

        return pauta;
    }
}
