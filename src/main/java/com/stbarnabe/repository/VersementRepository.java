package com.stbarnabe.repository;

import com.stbarnabe.domain.Versement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Versement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersementRepository extends JpaRepository<Versement, Long> {

    public List<Versement> findByFidele_Id(long id);
}
