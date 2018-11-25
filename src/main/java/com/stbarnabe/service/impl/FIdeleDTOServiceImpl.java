package com.stbarnabe.service.impl;

import com.stbarnabe.domain.Fidele;
import com.stbarnabe.domain.Versement;
import com.stbarnabe.repository.FideleRepository;
import com.stbarnabe.repository.VersementRepository;
import com.stbarnabe.service.FIdeleDTOService;
import com.stbarnabe.service.dto.FideleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Fidele.
 */
@Service
@Transactional
public class FIdeleDTOServiceImpl implements FIdeleDTOService {

    private final Logger log = LoggerFactory.getLogger(FIdeleDTOServiceImpl.class);

    private final FideleRepository fideleRepository;
    private final VersementRepository versementRepository;
    List<Versement> versements = new ArrayList<>();


    public FIdeleDTOServiceImpl(FideleRepository fideleRepository, VersementRepository versementRepository) {
        this.fideleRepository = fideleRepository;
        this.versementRepository = versementRepository;
    }


    /**
     * Get the "id" fidele.
     *
     * @param id the id of the Fidele
     * @return the entity
     */
    @Override
    public Optional<FideleDTO> findOne(Long id){
        log.debug("Request to get Fidele : {} with all his Versements", id);
        Fidele fidele = fideleRepository.getOne(id);
        versements = versementRepository.findByFidele_Id(id);
        FideleDTO fideleDTO = new FideleDTO();
        fideleDTO.setFidele(fidele);
        fideleDTO.setVersements(versements);
        return Optional.ofNullable(fideleDTO);
    }

    /**
     * Delete the "id" fidele and all his Versement.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id){
        log.debug("Request to delete Fidele : {} with all his Versements", id);
        versements = versementRepository.findByFidele_Id(id);
        versementRepository.deleteAll(versements);
        fideleRepository.deleteById(id);
    }

}
