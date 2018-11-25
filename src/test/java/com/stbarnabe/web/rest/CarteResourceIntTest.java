package com.stbarnabe.web.rest;

import com.stbarnabe.StBarnabeApp;

import com.stbarnabe.domain.Carte;
import com.stbarnabe.repository.CarteRepository;
import com.stbarnabe.service.CarteService;
import com.stbarnabe.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.stbarnabe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarteResource REST controller.
 *
 * @see CarteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StBarnabeApp.class)
public class CarteResourceIntTest {

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    private static final Long DEFAULT_MONTANT_MIN = 5000L;
    private static final Long UPDATED_MONTANT_MIN = 5001L;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private CarteService carteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarteMockMvc;

    private Carte carte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarteResource carteResource = new CarteResource(carteService);
        this.restCarteMockMvc = MockMvcBuilders.standaloneSetup(carteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carte createEntity(EntityManager em) {
        Carte carte = new Carte()
            .couleur(DEFAULT_COULEUR)
            .montantMin(DEFAULT_MONTANT_MIN);
        return carte;
    }

    @Before
    public void initTest() {
        carte = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarte() throws Exception {
        int databaseSizeBeforeCreate = carteRepository.findAll().size();

        // Create the Carte
        restCarteMockMvc.perform(post("/api/cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carte)))
            .andExpect(status().isCreated());

        // Validate the Carte in the database
        List<Carte> carteList = carteRepository.findAll();
        assertThat(carteList).hasSize(databaseSizeBeforeCreate + 1);
        Carte testCarte = carteList.get(carteList.size() - 1);
        assertThat(testCarte.getCouleur()).isEqualTo(DEFAULT_COULEUR);
        assertThat(testCarte.getMontantMin()).isEqualTo(DEFAULT_MONTANT_MIN);
    }

    @Test
    @Transactional
    public void createCarteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carteRepository.findAll().size();

        // Create the Carte with an existing ID
        carte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteMockMvc.perform(post("/api/cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carte)))
            .andExpect(status().isBadRequest());

        // Validate the Carte in the database
        List<Carte> carteList = carteRepository.findAll();
        assertThat(carteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCouleurIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteRepository.findAll().size();
        // set the field null
        carte.setCouleur(null);

        // Create the Carte, which fails.

        restCarteMockMvc.perform(post("/api/cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carte)))
            .andExpect(status().isBadRequest());

        List<Carte> carteList = carteRepository.findAll();
        assertThat(carteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteRepository.findAll().size();
        // set the field null
        carte.setMontantMin(null);

        // Create the Carte, which fails.

        restCarteMockMvc.perform(post("/api/cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carte)))
            .andExpect(status().isBadRequest());

        List<Carte> carteList = carteRepository.findAll();
        assertThat(carteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartes() throws Exception {
        // Initialize the database
        carteRepository.saveAndFlush(carte);

        // Get all the carteList
        restCarteMockMvc.perform(get("/api/cartes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carte.getId().intValue())))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR.toString())))
            .andExpect(jsonPath("$.[*].montantMin").value(hasItem(DEFAULT_MONTANT_MIN.intValue())));
    }
    
    @Test
    @Transactional
    public void getCarte() throws Exception {
        // Initialize the database
        carteRepository.saveAndFlush(carte);

        // Get the carte
        restCarteMockMvc.perform(get("/api/cartes/{id}", carte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carte.getId().intValue()))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR.toString()))
            .andExpect(jsonPath("$.montantMin").value(DEFAULT_MONTANT_MIN.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarte() throws Exception {
        // Get the carte
        restCarteMockMvc.perform(get("/api/cartes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarte() throws Exception {
        // Initialize the database
        carteService.save(carte);

        int databaseSizeBeforeUpdate = carteRepository.findAll().size();

        // Update the carte
        Carte updatedCarte = carteRepository.findById(carte.getId()).get();
        // Disconnect from session so that the updates on updatedCarte are not directly saved in db
        em.detach(updatedCarte);
        updatedCarte
            .couleur(UPDATED_COULEUR)
            .montantMin(UPDATED_MONTANT_MIN);

        restCarteMockMvc.perform(put("/api/cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarte)))
            .andExpect(status().isOk());

        // Validate the Carte in the database
        List<Carte> carteList = carteRepository.findAll();
        assertThat(carteList).hasSize(databaseSizeBeforeUpdate);
        Carte testCarte = carteList.get(carteList.size() - 1);
        assertThat(testCarte.getCouleur()).isEqualTo(UPDATED_COULEUR);
        assertThat(testCarte.getMontantMin()).isEqualTo(UPDATED_MONTANT_MIN);
    }

    @Test
    @Transactional
    public void updateNonExistingCarte() throws Exception {
        int databaseSizeBeforeUpdate = carteRepository.findAll().size();

        // Create the Carte

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteMockMvc.perform(put("/api/cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carte)))
            .andExpect(status().isBadRequest());

        // Validate the Carte in the database
        List<Carte> carteList = carteRepository.findAll();
        assertThat(carteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarte() throws Exception {
        // Initialize the database
        carteService.save(carte);

        int databaseSizeBeforeDelete = carteRepository.findAll().size();

        // Get the carte
        restCarteMockMvc.perform(delete("/api/cartes/{id}", carte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Carte> carteList = carteRepository.findAll();
        assertThat(carteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carte.class);
        Carte carte1 = new Carte();
        carte1.setId(1L);
        Carte carte2 = new Carte();
        carte2.setId(carte1.getId());
        assertThat(carte1).isEqualTo(carte2);
        carte2.setId(2L);
        assertThat(carte1).isNotEqualTo(carte2);
        carte1.setId(null);
        assertThat(carte1).isNotEqualTo(carte2);
    }
}
