package com.stbarnabe.service.impl;

import com.stbarnabe.domain.Versement;
import com.stbarnabe.repository.VersementRepository;
import com.stbarnabe.service.FideleService;
import com.stbarnabe.domain.Fidele;
import com.stbarnabe.domain.Carte;
import com.stbarnabe.repository.FideleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing Fidele.
 */
@Service
@Transactional
public class FideleServiceImpl implements FideleService {

    private final Logger log = LoggerFactory.getLogger(FideleServiceImpl.class);

    private final FideleRepository fideleRepository;
    private final VersementRepository versementRepository;

    public FideleServiceImpl(FideleRepository fideleRepository, VersementRepository versementRepository) {
        this.fideleRepository = fideleRepository;
        this.versementRepository = versementRepository;
    }

    /**
     * Save a fidele.
     *
     * @param fidele the entity to save
     * @return the persisted entity
     */
    @Override
    public Fidele save(Fidele fidele) {
        log.debug("Request to save Fidele : {}", fidele);
        Carte carte = fidele.getCarte();
        long montantRestant;
        Fidele fideleResult;
        if (fidele.getMontantVerse() > 0){
            montantRestant =  (carte.getMontantMin() - fidele.getMontantVerse()) >= 0 ?
                (carte.getMontantMin() - fidele.getMontantVerse()): 0;
            fidele.setMontantRestant(montantRestant);
            fideleResult = fideleRepository.save(fidele);
            Versement versement = new Versement();
            versement.setDateVersement(LocalDate.now());
            versement.setMontantVersement(fidele.getMontantVerse());
            versement.setFidele(fideleResult);
            log.debug("Request to save versement : {}", versement);
            versementRepository.save(versement);
        }else {
            fidele.setMontantRestant(carte.getMontantMin());
            fideleResult = fideleRepository.save(fidele);
        }
        return fideleResult;
    }

    /**
     * Get all the fideles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fidele> findAll(Pageable pageable) {
        log.debug("Request to get all Fideles");
        return fideleRepository.findAll(pageable);
    }


    /**
     * Get one fidele by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Fidele> findOne(Long id) {
        log.debug("Request to get Fidele : {}", id);
        return fideleRepository.findById(id);
    }

    /**
     * Delete the fidele by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fidele : {}", id);
        fideleRepository.deleteById(id);
    }
}
