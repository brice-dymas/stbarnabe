package com.stbarnabe.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Carte.
 */
@Entity
@Table(name = "carte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Carte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 4)
    @Column(name = "couleur", nullable = false)
    private String couleur;

    @NotNull
    @Min(value = 5000L)
    @Column(name = "montant_min", nullable = false)
    private Long montantMin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouleur() {
        return couleur;
    }

    public Carte couleur(String couleur) {
        this.couleur = couleur;
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Long getMontantMin() {
        return montantMin;
    }

    public Carte montantMin(Long montantMin) {
        this.montantMin = montantMin;
        return this;
    }

    public void setMontantMin(Long montantMin) {
        this.montantMin = montantMin;
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
        Carte carte = (Carte) o;
        if (carte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Carte{" +
            "id=" + getId() +
            ", couleur='" + getCouleur() + "'" +
            ", montantMin=" + getMontantMin() +
            "}";
    }
}
