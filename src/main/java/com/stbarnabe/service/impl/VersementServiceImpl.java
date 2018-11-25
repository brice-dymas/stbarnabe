package com.stbarnabe.service.impl;

import com.stbarnabe.domain.Fidele;
import com.stbarnabe.repository.FideleRepository;
import com.stbarnabe.service.VersementService;
import com.stbarnabe.domain.Versement;
import com.stbarnabe.repository.VersementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Versement.
 */
@Service
@Transactional
public class VersementServiceImpl implements VersementService {

    private final Logger log = LoggerFactory.getLogger(VersementServiceImpl.class);

    private final VersementRepository versementRepository;
    private FideleRepository fideleRepository;

    public VersementServiceImpl(VersementRepository versementRepository, FideleRepository fideleRepository) {
        this.versementRepository = versementRepository;
        this.fideleRepository = fideleRepository;
    }

    /**
     * Save a versement.
     *
     * @param versement the entity to save
     * @return the persisted entity
     */
    @Override
    public Versement save(Versement versement) {
        log.debug("Request to save Versement : {}", versement);
        Fidele fidele;
        long montantRestant;
        long montantVerse;
        if (versement.getId() != null){
            log.debug("Request to save Versement with ID");
            Versement oldVersement = versementRepository.getOne(versement.getId());
            fidele = fideleRepository.getOne(versement.getFidele().getId());
//            fidele.setMontantVerse(fidele.getMontantVerse() + versement.getMontantVersement());
            System.out.println("fidele.getMontantRestant() = "+fidele.getMontantRestant());
            System.out.println("versement.getMontantVersement() = "+versement.getMontantVersement());
            System.out.println("oldVersement.getMontantVersement() = "+oldVersement.getMontantVersement());
            montantRestant =  ((fidele.getMontantRestant() - versement.getMontantVersement()) + oldVersement.getMontantVersement()) >= 0 ?
                ((fidele.getMontantRestant() - versement.getMontantVersement()) + oldVersement.getMontantVersement()) : 0;
            System.out.println("montantRestant = " + montantRestant);
            montantVerse =  ((fidele.getMontantRestant() - montantRestant) + oldVersement.getMontantVersement()) >= 0 ?
                ((fidele.getMontantRestant() - montantRestant) + oldVersement.getMontantVersement()) : 0;
            fidele.setMontantRestant(montantRestant);
            fidele.setMontantVerse(montantVerse);
        }else {
            fidele = fideleRepository.getOne(versement.getFidele().getId());
            montantRestant = (fidele.getMontantRestant() - versement.getMontantVersement()) >= 0 ?
                (fidele.getMontantRestant() - versement.getMontantVersement()): 0;
            System.out.println("montantRestant = " + montantRestant);
            fidele.setMontantRestant(montantRestant);
            fidele.setMontantVerse(fidele.getMontantVerse()+versement.getMontantVersement());
        }
        fideleRepository.save(fidele);

        return versementRepository.save(versement);
    }

    /**
     * Get all the versements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Versement> findAll(Pageable pageable) {
        log.debug("Request to get all Versements");
        return versementRepository.findAll(pageable);
    }


    /**
     * Get one versement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Versement> findOne(Long id) {
        log.debug("Request to get Versement : {}", id);
        return versementRepository.findById(id);
    }

    /**
     * Delete the versement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Versement : {}", id);
        Versement versement =  versementRepository.getOne(id);
        Fidele fidele = versement.getFidele();
        fidele.setMontantRestant(fidele.getMontantRestant() + versement.getMontantVersement());
        fideleRepository.save(fidele);
        versementRepository.deleteById(id);
    }
}
