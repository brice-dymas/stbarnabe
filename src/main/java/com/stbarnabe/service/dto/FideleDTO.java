package com.stbarnabe.service.dto;

import com.stbarnabe.domain.Fidele;
import com.stbarnabe.domain.Versement;

import java.util.ArrayList;
import java.util.List;

public class FideleDTO {
    private Fidele fidele;
    private List<Versement> versements;

    public FideleDTO() {
        this.versements = new ArrayList<>();
    }

    public FideleDTO(Fidele fidele, List<Versement> versements) {
        this.fidele = fidele;
        this.versements = versements;
    }

    public Fidele getFidele() {
        return fidele;
    }

    public void setFidele(Fidele fidele) {
        this.fidele = fidele;
    }

    public List<Versement> getVersements() {
        return versements;
    }

    public void setVersements(List<Versement> versements) {
        this.versements = versements;
    }

    @Override
    public String toString() {
        return "FideleDTO{" +
            "fidele=" + fidele +
            ", versements=" + versements +
            '}';
    }
}
