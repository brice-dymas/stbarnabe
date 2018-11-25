package com.stbarnabe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stbarnabe.domain.Carte;
import com.stbarnabe.service.CarteService;
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
 * REST controller for managing Carte.
 */
@RestController
@RequestMapping("/api")
public class CarteResource {

    private final Logger log = LoggerFactory.getLogger(CarteResource.class);

    private static final String ENTITY_NAME = "carte";

    private final CarteService carteService;

    public CarteResource(CarteService carteService) {
        this.carteService = carteService;
    }

    /**
     * POST  /cartes : Create a new carte.
     *
     * @param carte the carte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carte, or with status 400 (Bad Request) if the carte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cartes")
    @Timed
    public ResponseEntity<Carte> createCarte(@Valid @RequestBody Carte carte) throws URISyntaxException {
        log.debug("REST request to save Carte : {}", carte);
        if (carte.getId() != null) {
            throw new BadRequestAlertException("A new carte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carte result = carteService.save(carte);
        return ResponseEntity.created(new URI("/api/cartes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cartes : Updates an existing carte.
     *
     * @param carte the carte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carte,
     * or with status 400 (Bad Request) if the carte is not valid,
     * or with status 500 (Internal Server Error) if the carte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cartes")
    @Timed
    public ResponseEntity<Carte> updateCarte(@Valid @RequestBody Carte carte) throws URISyntaxException {
        log.debug("REST request to update Carte : {}", carte);
        if (carte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Carte result = carteService.save(carte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cartes : get all the cartes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cartes in body
     */
    @GetMapping("/cartes")
    @Timed
    public ResponseEntity<List<Carte>> getAllCartes(Pageable pageable) {
        log.debug("REST request to get a page of Cartes");
        Page<Carte> page = carteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cartes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cartes/:id : get the "id" carte.
     *
     * @param id the id of the carte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carte, or with status 404 (Not Found)
     */
    @GetMapping("/cartes/{id}")
    @Timed
    public ResponseEntity<Carte> getCarte(@PathVariable Long id) {
        log.debug("REST request to get Carte : {}", id);
        Optional<Carte> carte = carteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carte);
    }

    /**
     * DELETE  /cartes/:id : delete the "id" carte.
     *
     * @param id the id of the carte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cartes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarte(@PathVariable Long id) {
        log.debug("REST request to delete Carte : {}", id);
        carteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
