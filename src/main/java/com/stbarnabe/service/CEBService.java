package com.stbarnabe.service;

import com.stbarnabe.domain.CEB;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CEB.
 */
public interface CEBService {

    /**
     * Save a cEB.
     *
     * @param cEB the entity to save
     * @return the persisted entity
     */
    CEB save(CEB cEB);

    /**
     * Get all the cEBS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CEB> findAll(Pageable pageable);


    /**
     * Get the "id" cEB.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CEB> findOne(Long id);

    /**
     * Delete the "id" cEB.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
