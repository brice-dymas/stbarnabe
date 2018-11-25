package com.stbarnabe.service;

import com.stbarnabe.domain.Fidele;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Fidele.
 */
public interface FideleService {

    /**
     * Save a fidele.
     *
     * @param fidele the entity to save
     * @return the persisted entity
     */
    Fidele save(Fidele fidele);

    /**
     * Get all the fideles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Fidele> findAll(Pageable pageable);


    /**
     * Get the "id" fidele.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Fidele> findOne(Long id);

    /**
     * Delete the "id" fidele.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
