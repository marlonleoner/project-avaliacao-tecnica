package marlon.leoner.avaliacao.tecnica.domains.utils;

import lombok.Getter;

@Getter
public enum VoteTypeEnum {

    YES(1, "Sim"),
    NO(0, "Não");

    private final Integer id;

    private final String description;

    VoteTypeEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static VoteTypeEnum getInstance(String vote) {
        for (VoteTypeEnum value : values()) {
            if (value.getDescription().equals(vote)) {
                return value;
            }
        }

        return null;
    }
}
