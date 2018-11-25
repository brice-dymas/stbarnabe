package com.stbarnabe.service.impl;

import com.stbarnabe.service.CarteService;
import com.stbarnabe.domain.Carte;
import com.stbarnabe.repository.CarteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Carte.
 */
@Service
@Transactional
public class CarteServiceImpl implements CarteService {

    private final Logger log = LoggerFactory.getLogger(CarteServiceImpl.class);

    private final CarteRepository carteRepository;

    public CarteServiceImpl(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    /**
     * Save a carte.
     *
     * @param carte the entity to save
     * @return the persisted entity
     */
    @Override
    public Carte save(Carte carte) {
        log.debug("Request to save Carte : {}", carte);
        return carteRepository.save(carte);
    }

    /**
     * Get all the cartes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Carte> findAll(Pageable pageable) {
        log.debug("Request to get all Cartes");
        return carteRepository.findAll(pageable);
    }


    /**
     * Get one carte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Carte> findOne(Long id) {
        log.debug("Request to get Carte : {}", id);
        return carteRepository.findById(id);
    }

    /**
     * Delete the carte by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Carte : {}", id);
        carteRepository.deleteById(id);
    }
}
