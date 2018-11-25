package com.stbarnabe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stbarnabe.listeners.VersementListener;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Versement.
 */
@Entity
@Table(name = "versement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@EntityListeners(VersementListener.class)
public class Versement implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_versement")
    private LocalDate dateVersement;

    @NotNull
    @Min(value = 100L)
    @Column(name = "montant_versement", nullable = false)
    private Long montantVersement;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Fidele fidele;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Employe employe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateVersement() {
        return dateVersement;
    }

    public Versement dateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
        return this;
    }

    public void setDateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
    }

    public Long getMontantVersement() {
        return montantVersement;
    }

    public Versement montantVersement(Long montantVersement) {
        this.montantVersement = montantVersement;
        return this;
    }

    public void setMontantVersement(Long montantVersement) {
        this.montantVersement = montantVersement;
    }

    public Fidele getFidele() {
        return fidele;
    }

    public Versement fidele(Fidele fidele) {
        this.fidele = fidele;
        return this;
    }

    public void setFidele(Fidele fidele) {
        this.fidele = fidele;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Versement employe(Employe employe) {
        this.employe = employe;
        return this;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Versement versement = (Versement) o;
        if (versement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), versement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Versement{" +
            "id=" + id +
            ", dateVersement=" + dateVersement +
            ", montantVersement=" + montantVersement +
            ", fidele=" + fidele +
            '}';
    }
}
