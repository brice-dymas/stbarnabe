package com.stbarnabe.repository;

import com.stbarnabe.domain.Fidele;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fidele entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FideleRepository extends JpaRepository<Fidele, Long> {

}
