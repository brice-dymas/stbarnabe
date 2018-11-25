package com.stbarnabe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stbarnabe.domain.Fidele;
import com.stbarnabe.service.FIdeleDTOService;
import com.stbarnabe.service.FideleService;
import com.stbarnabe.service.dto.FideleDTO;
import com.stbarnabe.web.rest.errors.BadRequestAlertException;
import com.stbarnabe.web.rest.util.HeaderUtil;
import com.stbarnabe.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fidele.
 */
@RestController
@RequestMapping("/api")
public class FideleDTOResource {

    private final Logger log = LoggerFactory.getLogger(FideleDTOResource.class);

    private static final String ENTITY_NAME = "fidele";

    private final FIdeleDTOService fideleService;

    public FideleDTOResource(FIdeleDTOService fideleService) {
        this.fideleService = fideleService;
    }

    /**
     * GET  /fideles/:id/versements : get the "id" fidele.
     *
     * @param id the id of the fidele to retrieve with his versements
     * @return the ResponseEntity with status 200 (OK) and with body the fidele, or with status 404 (Not Found)
     */
    @GetMapping("/fideles-versements/{id}")
    @Timed
    public ResponseEntity<FideleDTO> getFidele(@PathVariable Long id) {
        log.debug("REST request to get Fidele : {} and all his versements", id);
        Optional<FideleDTO> fidele = fideleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fidele);
    }

    /**
     * DELETE  /fideles/:id : delete the "id" fidele.
     *
     * @param id the id of the fidele to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fideles-versements/{id}")
    @Timed
    public ResponseEntity<Void> deleteFidele(@PathVariable Long id) {
        log.debug("REST request to delete Fidele : {}", id);
        fideleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
