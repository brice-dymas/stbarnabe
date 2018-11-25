package com.stbarnabe.listeners;

import com.stbarnabe.domain.Fidele;
import com.stbarnabe.domain.Versement;
import com.stbarnabe.repository.FideleRepository;
import com.stbarnabe.repository.VersementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Transactional
public class VersementListener {

    @Autowired
    private FideleRepository fideleRepository;

    @Autowired
    private VersementRepository versementRepository;

    @PostPersist
    public void updateResteaPayerFidele(Versement versement){
        System.out.println("THE FIDELE TO USE IN SAVE OF VERSEMENT IS " + versement);
        Fidele fidele = fideleRepository.getOne(versement.getFidele().getId());
        long montantRestant =  (fidele.getMontantRestant() - versement.getMontantVersement()) >= 0 ?
            (fidele.getMontantRestant() - versement.getMontantVersement()): 0;
        fidele.setMontantRestant(montantRestant);
        fideleRepository.save(fidele);
    }

    @PreRemove
    public void updateResteaPayerFideleBeforeRemove(Versement versement){
        Fidele fidele = fideleRepository.getOne(versement.getFidele().getId());
        fidele.setMontantRestant(fidele.getMontantRestant() + versement.getMontantVersement());
        fideleRepository.save(fidele);
    }

    @PreUpdate
    public void updateResteaPayerFideleAfterUpdate(Versement versement){
        Versement oldVersement = versementRepository.getOne(versement.getId());
        Fidele fidele = fideleRepository.getOne(versement.getFidele().getId());
        fidele.setMontantRestant(fidele.getMontantRestant() - oldVersement.getMontantVersement() + versement.getMontantVersement());
        long montantRestant =  (fidele.getMontantRestant() - oldVersement.getMontantVersement() + versement.getMontantVersement()) >= 0 ?
            (fidele.getMontantRestant() - oldVersement.getMontantVersement() + versement.getMontantVersement()) : 0;
        fidele.setMontantRestant(montantRestant);
        fideleRepository.save(fidele);
    }
}
