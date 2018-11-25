package com.stbarnabe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stbarnabe.domain.CEB;
import com.stbarnabe.service.CEBService;
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
 * REST controller for managing CEB.
 */
@RestController
@RequestMapping("/api")
public class CEBResource {

    private final Logger log = LoggerFactory.getLogger(CEBResource.class);

    private static final String ENTITY_NAME = "cEB";

    private final CEBService cEBService;

    public CEBResource(CEBService cEBService) {
        this.cEBService = cEBService;
    }

    /**
     * POST  /cebs : Create a new cEB.
     *
     * @param cEB the cEB to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cEB, or with status 400 (Bad Request) if the cEB has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cebs")
    @Timed
    public ResponseEntity<CEB> createCEB(@Valid @RequestBody CEB cEB) throws URISyntaxException {
        log.debug("REST request to save CEB : {}", cEB);
        if (cEB.getId() != null) {
            throw new BadRequestAlertException("A new cEB cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEB result = cEBService.save(cEB);
        return ResponseEntity.created(new URI("/api/cebs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cebs : Updates an existing cEB.
     *
     * @param cEB the cEB to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cEB,
     * or with status 400 (Bad Request) if the cEB is not valid,
     * or with status 500 (Internal Server Error) if the cEB couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cebs")
    @Timed
    public ResponseEntity<CEB> updateCEB(@Valid @RequestBody CEB cEB) throws URISyntaxException {
        log.debug("REST request to update CEB : {}", cEB);
        if (cEB.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEB result = cEBService.save(cEB);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cEB.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cebs : get all the cEBS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cEBS in body
     */
    @GetMapping("/cebs")
    @Timed
    public ResponseEntity<List<CEB>> getAllCEBS(Pageable pageable) {
        log.debug("REST request to get a page of CEBS");
        Page<CEB> page = cEBService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cebs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cebs/:id : get the "id" cEB.
     *
     * @param id the id of the cEB to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cEB, or with status 404 (Not Found)
     */
    @GetMapping("/cebs/{id}")
    @Timed
    public ResponseEntity<CEB> getCEB(@PathVariable Long id) {
        log.debug("REST request to get CEB : {}", id);
        Optional<CEB> cEB = cEBService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEB);
    }

    /**
     * DELETE  /cebs/:id : delete the "id" cEB.
     *
     * @param id the id of the cEB to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cebs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCEB(@PathVariable Long id) {
        log.debug("REST request to delete CEB : {}", id);
        cEBService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
