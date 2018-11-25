package com.stbarnabe.service;

import com.stbarnabe.domain.Carte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Carte.
 */
public interface CarteService {

    /**
     * Save a carte.
     *
     * @param carte the entity to save
     * @return the persisted entity
     */
    Carte save(Carte carte);

    /**
     * Get all the cartes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Carte> findAll(Pageable pageable);


    /**
     * Get the "id" carte.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Carte> findOne(Long id);

    /**
     * Delete the "id" carte.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
