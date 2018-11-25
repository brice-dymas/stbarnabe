package com.stbarnabe.service.impl;

import com.stbarnabe.service.CEBService;
import com.stbarnabe.domain.CEB;
import com.stbarnabe.repository.CEBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CEB.
 */
@Service
@Transactional
public class CEBServiceImpl implements CEBService {

    private final Logger log = LoggerFactory.getLogger(CEBServiceImpl.class);

    private final CEBRepository cEBRepository;

    public CEBServiceImpl(CEBRepository cEBRepository) {
        this.cEBRepository = cEBRepository;
    }

    /**
     * Save a cEB.
     *
     * @param cEB the entity to save
     * @return the persisted entity
     */
    @Override
    public CEB save(CEB cEB) {
        log.debug("Request to save CEB : {}", cEB);
        return cEBRepository.save(cEB);
    }

    /**
     * Get all the cEBS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CEB> findAll(Pageable pageable) {
        log.debug("Request to get all CEBS");
        return cEBRepository.findAll(pageable);
    }


    /**
     * Get one cEB by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CEB> findOne(Long id) {
        log.debug("Request to get CEB : {}", id);
        return cEBRepository.findById(id);
    }

    /**
     * Delete the cEB by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CEB : {}", id);
        cEBRepository.deleteById(id);
    }
}
