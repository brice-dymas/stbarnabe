package com.stbarnabe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.stbarnabe.domain.enumeration.Categorie;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "fidele")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fidele implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private Categorie categorie;

    @Min(value = 0L)
    @Column(name = "montant_verse")
    private Long montantVerse;

    @Min(value = 0L)
    @Column(name = "montant_restant")
    private Long montantRestant;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Carte carte;

    @ManyToOne
    @JsonIgnoreProperties("")
    private CEB ceb;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Fidele nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Fidele prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Fidele categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Long getMontantVerse() {
        return montantVerse;
    }

    public Fidele montantVerse(Long montantVerse) {
        this.montantVerse = montantVerse;
        return this;
    }

    public void setMontantVerse(Long montantVerse) {
        this.montantVerse = montantVerse;
    }

    public Long getMontantRestant() {
        return montantRestant;
    }

    public Fidele montantRestant(Long montantRestant) {
        this.montantRestant = montantRestant;
        return this;
    }

    public void setMontantRestant(Long montantRestant) {
        this.montantRestant = montantRestant;
    }

    public Carte getCarte() {
        return carte;
    }

    public Fidele carte(Carte carte) {
        this.carte = carte;
        return this;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public CEB getCeb() {
        return ceb;
    }

    public Fidele ceb(CEB cEB) {
        this.ceb = cEB;
        return this;
    }

    public void setCeb(CEB cEB) {
        this.ceb = cEB;
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
        Fidele fidele = (Fidele) o;
        if (fidele.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fidele.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fidele{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", montantVerse=" + getMontantVerse() +
            ", montantRestant=" + getMontantRestant() +
            "}";
    }
}
