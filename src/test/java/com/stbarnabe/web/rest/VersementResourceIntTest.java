package com.stbarnabe.web.rest;

import com.stbarnabe.StBarnabeApp;

import com.stbarnabe.domain.Versement;
import com.stbarnabe.repository.VersementRepository;
import com.stbarnabe.service.VersementService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.stbarnabe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VersementResource REST controller.
 *
 * @see VersementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StBarnabeApp.class)
public class VersementResourceIntTest {

    private static final LocalDate DEFAULT_DATE_VERSEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VERSEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MONTANT_VERSEMENT = 100L;
    private static final Long UPDATED_MONTANT_VERSEMENT = 101L;

    @Autowired
    private VersementRepository versementRepository;

    @Autowired
    private VersementService versementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVersementMockMvc;

    private Versement versement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VersementResource versementResource = new VersementResource(versementService);
        this.restVersementMockMvc = MockMvcBuilders.standaloneSetup(versementResource)
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
    public static Versement createEntity(EntityManager em) {
        Versement versement = new Versement()
            .dateVersement(DEFAULT_DATE_VERSEMENT)
            .montantVersement(DEFAULT_MONTANT_VERSEMENT);
        return versement;
    }

    @Before
    public void initTest() {
        versement = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersement() throws Exception {
        int databaseSizeBeforeCreate = versementRepository.findAll().size();

        // Create the Versement
        restVersementMockMvc.perform(post("/api/versements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versement)))
            .andExpect(status().isCreated());

        // Validate the Versement in the database
        List<Versement> versementList = versementRepository.findAll();
        assertThat(versementList).hasSize(databaseSizeBeforeCreate + 1);
        Versement testVersement = versementList.get(versementList.size() - 1);
        assertThat(testVersement.getDateVersement()).isEqualTo(DEFAULT_DATE_VERSEMENT);
        assertThat(testVersement.getMontantVersement()).isEqualTo(DEFAULT_MONTANT_VERSEMENT);
    }

    @Test
    @Transactional
    public void createVersementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versementRepository.findAll().size();

        // Create the Versement with an existing ID
        versement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersementMockMvc.perform(post("/api/versements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versement)))
            .andExpect(status().isBadRequest());

        // Validate the Versement in the database
        List<Versement> versementList = versementRepository.findAll();
        assertThat(versementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantVersementIsRequired() throws Exception {
        int databaseSizeBeforeTest = versementRepository.findAll().size();
        // set the field null
        versement.setMontantVersement(null);

        // Create the Versement, which fails.

        restVersementMockMvc.perform(post("/api/versements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versement)))
            .andExpect(status().isBadRequest());

        List<Versement> versementList = versementRepository.findAll();
        assertThat(versementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersements() throws Exception {
        // Initialize the database
        versementRepository.saveAndFlush(versement);

        // Get all the versementList
        restVersementMockMvc.perform(get("/api/versements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateVersement").value(hasItem(DEFAULT_DATE_VERSEMENT.toString())))
            .andExpect(jsonPath("$.[*].montantVersement").value(hasItem(DEFAULT_MONTANT_VERSEMENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getVersement() throws Exception {
        // Initialize the database
        versementRepository.saveAndFlush(versement);

        // Get the versement
        restVersementMockMvc.perform(get("/api/versements/{id}", versement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(versement.getId().intValue()))
            .andExpect(jsonPath("$.dateVersement").value(DEFAULT_DATE_VERSEMENT.toString()))
            .andExpect(jsonPath("$.montantVersement").value(DEFAULT_MONTANT_VERSEMENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVersement() throws Exception {
        // Get the versement
        restVersementMockMvc.perform(get("/api/versements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersement() throws Exception {
        // Initialize the database
        versementService.save(versement);

        int databaseSizeBeforeUpdate = versementRepository.findAll().size();

        // Update the versement
        Versement updatedVersement = versementRepository.findById(versement.getId()).get();
        // Disconnect from session so that the updates on updatedVersement are not directly saved in db
        em.detach(updatedVersement);
        updatedVersement
            .dateVersement(UPDATED_DATE_VERSEMENT)
            .montantVersement(UPDATED_MONTANT_VERSEMENT);

        restVersementMockMvc.perform(put("/api/versements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersement)))
            .andExpect(status().isOk());

        // Validate the Versement in the database
        List<Versement> versementList = versementRepository.findAll();
        assertThat(versementList).hasSize(databaseSizeBeforeUpdate);
        Versement testVersement = versementList.get(versementList.size() - 1);
        assertThat(testVersement.getDateVersement()).isEqualTo(UPDATED_DATE_VERSEMENT);
        assertThat(testVersement.getMontantVersement()).isEqualTo(UPDATED_MONTANT_VERSEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingVersement() throws Exception {
        int databaseSizeBeforeUpdate = versementRepository.findAll().size();

        // Create the Versement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersementMockMvc.perform(put("/api/versements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versement)))
            .andExpect(status().isBadRequest());

        // Validate the Versement in the database
        List<Versement> versementList = versementRepository.findAll();
        assertThat(versementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVersement() throws Exception {
        // Initialize the database
        versementService.save(versement);

        int databaseSizeBeforeDelete = versementRepository.findAll().size();

        // Get the versement
        restVersementMockMvc.perform(delete("/api/versements/{id}", versement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Versement> versementList = versementRepository.findAll();
        assertThat(versementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Versement.class);
        Versement versement1 = new Versement();
        versement1.setId(1L);
        Versement versement2 = new Versement();
        versement2.setId(versement1.getId());
        assertThat(versement1).isEqualTo(versement2);
        versement2.setId(2L);
        assertThat(versement1).isNotEqualTo(versement2);
        versement1.setId(null);
        assertThat(versement1).isNotEqualTo(versement2);
    }
}
