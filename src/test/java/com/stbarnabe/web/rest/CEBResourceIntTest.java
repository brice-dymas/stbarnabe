package com.stbarnabe.web.rest;

import com.stbarnabe.StBarnabeApp;

import com.stbarnabe.domain.CEB;
import com.stbarnabe.repository.CEBRepository;
import com.stbarnabe.service.CEBService;
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
 * Test class for the CEBResource REST controller.
 *
 * @see CEBResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StBarnabeApp.class)
public class CEBResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private CEBRepository cEBRepository;

    @Autowired
    private CEBService cEBService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCEBMockMvc;

    private CEB cEB;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CEBResource cEBResource = new CEBResource(cEBService);
        this.restCEBMockMvc = MockMvcBuilders.standaloneSetup(cEBResource)
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
    public static CEB createEntity(EntityManager em) {
        CEB cEB = new CEB()
            .nom(DEFAULT_NOM);
        return cEB;
    }

    @Before
    public void initTest() {
        cEB = createEntity(em);
    }

    @Test
    @Transactional
    public void createCEB() throws Exception {
        int databaseSizeBeforeCreate = cEBRepository.findAll().size();

        // Create the CEB
        restCEBMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEB)))
            .andExpect(status().isCreated());

        // Validate the CEB in the database
        List<CEB> cEBList = cEBRepository.findAll();
        assertThat(cEBList).hasSize(databaseSizeBeforeCreate + 1);
        CEB testCEB = cEBList.get(cEBList.size() - 1);
        assertThat(testCEB.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createCEBWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cEBRepository.findAll().size();

        // Create the CEB with an existing ID
        cEB.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCEBMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEB)))
            .andExpect(status().isBadRequest());

        // Validate the CEB in the database
        List<CEB> cEBList = cEBRepository.findAll();
        assertThat(cEBList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = cEBRepository.findAll().size();
        // set the field null
        cEB.setNom(null);

        // Create the CEB, which fails.

        restCEBMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEB)))
            .andExpect(status().isBadRequest());

        List<CEB> cEBList = cEBRepository.findAll();
        assertThat(cEBList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCEBS() throws Exception {
        // Initialize the database
        cEBRepository.saveAndFlush(cEB);

        // Get all the cEBList
        restCEBMockMvc.perform(get("/api/cebs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cEB.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }
    
    @Test
    @Transactional
    public void getCEB() throws Exception {
        // Initialize the database
        cEBRepository.saveAndFlush(cEB);

        // Get the cEB
        restCEBMockMvc.perform(get("/api/cebs/{id}", cEB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cEB.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCEB() throws Exception {
        // Get the cEB
        restCEBMockMvc.perform(get("/api/cebs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCEB() throws Exception {
        // Initialize the database
        cEBService.save(cEB);

        int databaseSizeBeforeUpdate = cEBRepository.findAll().size();

        // Update the cEB
        CEB updatedCEB = cEBRepository.findById(cEB.getId()).get();
        // Disconnect from session so that the updates on updatedCEB are not directly saved in db
        em.detach(updatedCEB);
        updatedCEB
            .nom(UPDATED_NOM);

        restCEBMockMvc.perform(put("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCEB)))
            .andExpect(status().isOk());

        // Validate the CEB in the database
        List<CEB> cEBList = cEBRepository.findAll();
        assertThat(cEBList).hasSize(databaseSizeBeforeUpdate);
        CEB testCEB = cEBList.get(cEBList.size() - 1);
        assertThat(testCEB.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingCEB() throws Exception {
        int databaseSizeBeforeUpdate = cEBRepository.findAll().size();

        // Create the CEB

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCEBMockMvc.perform(put("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cEB)))
            .andExpect(status().isBadRequest());

        // Validate the CEB in the database
        List<CEB> cEBList = cEBRepository.findAll();
        assertThat(cEBList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCEB() throws Exception {
        // Initialize the database
        cEBService.save(cEB);

        int databaseSizeBeforeDelete = cEBRepository.findAll().size();

        // Get the cEB
        restCEBMockMvc.perform(delete("/api/cebs/{id}", cEB.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CEB> cEBList = cEBRepository.findAll();
        assertThat(cEBList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEB.class);
        CEB cEB1 = new CEB();
        cEB1.setId(1L);
        CEB cEB2 = new CEB();
        cEB2.setId(cEB1.getId());
        assertThat(cEB1).isEqualTo(cEB2);
        cEB2.setId(2L);
        assertThat(cEB1).isNotEqualTo(cEB2);
        cEB1.setId(null);
        assertThat(cEB1).isNotEqualTo(cEB2);
    }
}
