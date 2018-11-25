package com.stbarnabe.web.rest;

import com.stbarnabe.StBarnabeApp;

import com.stbarnabe.domain.Fidele;
import com.stbarnabe.repository.FideleRepository;
import com.stbarnabe.service.FideleService;
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

import com.stbarnabe.domain.enumeration.Categorie;
/**
 * Test class for the FideleResource REST controller.
 *
 * @see FideleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StBarnabeApp.class)
public class FideleResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Categorie DEFAULT_CATEGORIE = Categorie.ANONYME;
    private static final Categorie UPDATED_CATEGORIE = Categorie.FAMILLE;

    private static final Long DEFAULT_MONTANT_VERSE = 0L;
    private static final Long UPDATED_MONTANT_VERSE = 1L;

    private static final Long DEFAULT_MONTANT_RESTANT = 0L;
    private static final Long UPDATED_MONTANT_RESTANT = 1L;

    @Autowired
    private FideleRepository fideleRepository;

    @Autowired
    private FideleService fideleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFideleMockMvc;

    private Fidele fidele;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FideleResource fideleResource = new FideleResource(fideleService);
        this.restFideleMockMvc = MockMvcBuilders.standaloneSetup(fideleResource)
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
    public static Fidele createEntity(EntityManager em) {
        Fidele fidele = new Fidele()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .categorie(DEFAULT_CATEGORIE)
            .montantVerse(DEFAULT_MONTANT_VERSE)
            .montantRestant(DEFAULT_MONTANT_RESTANT);
        return fidele;
    }

    @Before
    public void initTest() {
        fidele = createEntity(em);
    }

    @Test
    @Transactional
    public void createFidele() throws Exception {
        int databaseSizeBeforeCreate = fideleRepository.findAll().size();

        // Create the Fidele
        restFideleMockMvc.perform(post("/api/fideles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fidele)))
            .andExpect(status().isCreated());

        // Validate the Fidele in the database
        List<Fidele> fideleList = fideleRepository.findAll();
        assertThat(fideleList).hasSize(databaseSizeBeforeCreate + 1);
        Fidele testFidele = fideleList.get(fideleList.size() - 1);
        assertThat(testFidele.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFidele.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testFidele.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testFidele.getMontantVerse()).isEqualTo(DEFAULT_MONTANT_VERSE);
        assertThat(testFidele.getMontantRestant()).isEqualTo(DEFAULT_MONTANT_RESTANT);
    }

    @Test
    @Transactional
    public void createFideleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fideleRepository.findAll().size();

        // Create the Fidele with an existing ID
        fidele.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFideleMockMvc.perform(post("/api/fideles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fidele)))
            .andExpect(status().isBadRequest());

        // Validate the Fidele in the database
        List<Fidele> fideleList = fideleRepository.findAll();
        assertThat(fideleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = fideleRepository.findAll().size();
        // set the field null
        fidele.setNom(null);

        // Create the Fidele, which fails.

        restFideleMockMvc.perform(post("/api/fideles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fidele)))
            .andExpect(status().isBadRequest());

        List<Fidele> fideleList = fideleRepository.findAll();
        assertThat(fideleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFideles() throws Exception {
        // Initialize the database
        fideleRepository.saveAndFlush(fidele);

        // Get all the fideleList
        restFideleMockMvc.perform(get("/api/fideles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fidele.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].montantVerse").value(hasItem(DEFAULT_MONTANT_VERSE.intValue())))
            .andExpect(jsonPath("$.[*].montantRestant").value(hasItem(DEFAULT_MONTANT_RESTANT.intValue())));
    }
    
    @Test
    @Transactional
    public void getFidele() throws Exception {
        // Initialize the database
        fideleRepository.saveAndFlush(fidele);

        // Get the fidele
        restFideleMockMvc.perform(get("/api/fideles/{id}", fidele.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fidele.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.montantVerse").value(DEFAULT_MONTANT_VERSE.intValue()))
            .andExpect(jsonPath("$.montantRestant").value(DEFAULT_MONTANT_RESTANT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFidele() throws Exception {
        // Get the fidele
        restFideleMockMvc.perform(get("/api/fideles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFidele() throws Exception {
        // Initialize the database
        fideleService.save(fidele);

        int databaseSizeBeforeUpdate = fideleRepository.findAll().size();

        // Update the fidele
        Fidele updatedFidele = fideleRepository.findById(fidele.getId()).get();
        // Disconnect from session so that the updates on updatedFidele are not directly saved in db
        em.detach(updatedFidele);
        updatedFidele
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .categorie(UPDATED_CATEGORIE)
            .montantVerse(UPDATED_MONTANT_VERSE)
            .montantRestant(UPDATED_MONTANT_RESTANT);

        restFideleMockMvc.perform(put("/api/fideles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFidele)))
            .andExpect(status().isOk());

        // Validate the Fidele in the database
        List<Fidele> fideleList = fideleRepository.findAll();
        assertThat(fideleList).hasSize(databaseSizeBeforeUpdate);
        Fidele testFidele = fideleList.get(fideleList.size() - 1);
        assertThat(testFidele.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFidele.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFidele.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testFidele.getMontantVerse()).isEqualTo(UPDATED_MONTANT_VERSE);
        assertThat(testFidele.getMontantRestant()).isEqualTo(UPDATED_MONTANT_RESTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingFidele() throws Exception {
        int databaseSizeBeforeUpdate = fideleRepository.findAll().size();

        // Create the Fidele

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFideleMockMvc.perform(put("/api/fideles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fidele)))
            .andExpect(status().isBadRequest());

        // Validate the Fidele in the database
        List<Fidele> fideleList = fideleRepository.findAll();
        assertThat(fideleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFidele() throws Exception {
        // Initialize the database
        fideleService.save(fidele);

        int databaseSizeBeforeDelete = fideleRepository.findAll().size();

        // Get the fidele
        restFideleMockMvc.perform(delete("/api/fideles/{id}", fidele.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fidele> fideleList = fideleRepository.findAll();
        assertThat(fideleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fidele.class);
        Fidele fidele1 = new Fidele();
        fidele1.setId(1L);
        Fidele fidele2 = new Fidele();
        fidele2.setId(fidele1.getId());
        assertThat(fidele1).isEqualTo(fidele2);
        fidele2.setId(2L);
        assertThat(fidele1).isNotEqualTo(fidele2);
        fidele1.setId(null);
        assertThat(fidele1).isNotEqualTo(fidele2);
    }
}
