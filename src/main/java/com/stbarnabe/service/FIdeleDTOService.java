package com.stbarnabe.service;

import com.stbarnabe.service.dto.FideleDTO;

import java.util.Optional;

public interface FIdeleDTOService {

    /**
     * Get the "id" fidele.
     *
     * @param id the id of the Fidele
     * @return the entity
     */
    Optional<FideleDTO> findOne(Long id);

    /**
     * Delete the "id" fidele and all his Versement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
