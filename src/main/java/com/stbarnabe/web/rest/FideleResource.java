package com.stbarnabe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stbarnabe.domain.Fidele;
import com.stbarnabe.service.FideleService;
import com.stbarnabe.web.rest.errors.BadRequestAlertException;
import com.stbarnabe.web.rest.util.HeaderUtil;
import com.stbarnabe.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class FideleResource {

    private final Logger log = LoggerFactory.getLogger(FideleResource.class);

    private static final String ENTITY_NAME = "fidele";

    private final FideleService fideleService;

    public FideleResource(FideleService fideleService) {
        this.fideleService = fideleService;
    }

    /**
     * POST  /fideles : Create a new fidele.
     *
     * @param fidele the fidele to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fidele, or with status 400 (Bad Request) if the fidele has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fideles")
    @Timed
    public ResponseEntity<Fidele> createFidele(@Valid @RequestBody Fidele fidele) throws URISyntaxException {
        log.debug("REST request to save Fidele : {}", fidele);
        if (fidele.getId() != null) {
            throw new BadRequestAlertException("A new fidele cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fidele result = fideleService.save(fidele);
        return ResponseEntity.created(new URI("/api/fideles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fideles : Updates an existing fidele.
     *
     * @param fidele the fidele to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fidele,
     * or with status 400 (Bad Request) if the fidele is not valid,
     * or with status 500 (Internal Server Error) if the fidele couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fideles")
    @Timed
    public ResponseEntity<Fidele> updateFidele(@Valid @RequestBody Fidele fidele) throws URISyntaxException {
        log.debug("REST request to update Fidele : {}", fidele);
        if (fidele.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fidele result = fideleService.save(fidele);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fidele.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fideles : get all the fideles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fideles in body
     */
    @GetMapping("/fideles")
    @Timed
    public ResponseEntity<List<Fidele>> getAllFideles(Pageable pageable) {
        log.debug("REST request to get a page of Fideles");
        Page<Fidele> page = fideleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fideles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /fideles/:id : get the "id" fidele.
     *
     * @param id the id of the fidele to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fidele, or with status 404 (Not Found)
     */
    @GetMapping("/fideles/{id}")
    @Timed
    public ResponseEntity<Fidele> getFidele(@PathVariable Long id) {
        log.debug("REST request to get Fidele : {}", id);
        Optional<Fidele> fidele = fideleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fidele);
    }

    /**
     * DELETE  /fideles/:id : delete the "id" fidele.
     *
     * @param id the id of the fidele to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fideles/{id}")
    @Timed
    public ResponseEntity<Void> deleteFidele(@PathVariable Long id) {
        log.debug("REST request to delete Fidele : {}", id);
        fideleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
