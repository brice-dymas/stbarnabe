package com.stbarnabe.repository;

import com.stbarnabe.domain.CEB;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CEB entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEBRepository extends JpaRepository<CEB, Long> {

}
