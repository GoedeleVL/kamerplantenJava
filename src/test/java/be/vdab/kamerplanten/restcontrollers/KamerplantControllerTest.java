package be.vdab.kamerplanten.restcontrollers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/insertKamerplant.sql")
class KamerplantControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final MockMvc mvc;

    public KamerplantControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    private static String NIEUWE_PLANT_ALS_JSON_STRING = "{ \"naam\": \"test2\", \"tijdinterval\": \"1\", \"hoeveelheid\": \"test\", \"watergekregen\": \"2020-01-01\"}";

    private long idVanTestKamerplant() {
        var id = super.jdbcTemplate.queryForObject(
                "select id from kamerplanten where naam='test'", Long.class);
        return id;
    }

    private String testKamerPlantAlsJsonString() {
        var id = idVanTestKamerplant();
        return "{ \"id\" : " + id + ", \"naam\": \"test2\", \"tijdinterval\": \"1\", \"hoeveelheid\": \"test\", \"watergekregen\": \"2020-01-01\"}";
    }

    @Test
    void onbestaandeKamerplantLezen() throws Exception {
        mvc.perform(get("/planten/{id}", -1))
                .andExpect(status().isNotFound());
    }

    @Test
    void kamerplantLezen() throws Exception {
        var id = idVanTestKamerplant();
        mvc.perform(get("/planten/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id));
    }

    @Test
    void dorstigeKamerplantenLezen() throws Exception {
        mvc.perform(get("/planten/dorstig"))
                .andExpect(status().isOk());
    }

    @Test
    void alleKamerplantenLezen() throws Exception {
        mvc.perform(get("/planten"))
                .andExpect(status().isOk());
    }

    @Test
    void kamerplantDeleten() throws Exception {
        var id = idVanTestKamerplant();
        mvc.perform(delete("/planten/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void onbestaandeKamerplantDeleten() throws Exception {
        mvc.perform(delete("/planten/{id}", -1))
                .andExpect(status().isNotFound());
    }

    @Test
    void kamerplantToevoegen() throws Exception {
        mvc.perform(post("/planten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(NIEUWE_PLANT_ALS_JSON_STRING))
                .andExpect(status().isCreated());
    }

    @Test
    void ongeldigeKamerplantToevoegen() throws Exception {
        mvc.perform(post("/planten")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"naam\": \"test\"}"))
        .andExpect(status().isBadRequest());
    }

    @Test
    void kamerplantUpdaten() throws Exception {
        var plantAlsJsonString = testKamerPlantAlsJsonString();
        System.out.println(plantAlsJsonString);
        mvc.perform(put("/planten/{id}", idVanTestKamerplant())
                .contentType(MediaType.APPLICATION_JSON)
                .content(NIEUWE_PLANT_ALS_JSON_STRING))
                .andExpect(status().isOk());
    }

    @Test
    void kamerplantUpdatenMetOngeldigeContent() throws Exception {
        mvc.perform(put("/planten/{id}", idVanTestKamerplant())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"naam\": \"test\"}"))
                .andExpect(status().isBadRequest());
    }

}
