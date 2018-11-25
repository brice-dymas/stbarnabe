package com.stbarnabe.service;

import com.stbarnabe.domain.Versement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Versement.
 */
public interface VersementService {

    /**
     * Save a versement.
     *
     * @param versement the entity to save
     * @return the persisted entity
     */
    Versement save(Versement versement);

    /**
     * Get all the versements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Versement> findAll(Pageable pageable);


    /**
     * Get the "id" versement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Versement> findOne(Long id);

    /**
     * Delete the "id" versement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
