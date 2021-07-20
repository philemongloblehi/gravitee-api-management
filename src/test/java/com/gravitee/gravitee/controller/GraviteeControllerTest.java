package com.gravitee.gravitee.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class GraviteeControllerTest {
    @Autowired
    private MockMvc mvc;

    private static JSONObject responseCreateApi;
    private static JSONObject responseCreatePlan;

    @BeforeAll
    public void setUp() {
        responseCreateApi = null;
        responseCreatePlan = null;
    }

    @AfterAll
    public void tearDown() {
        responseCreateApi = null;
        responseCreatePlan = null;
    }

    @Test
    @Order(value = 0)
    public void testThatCanCreateApiRequest() throws Exception {
        MvcResult result = this.mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/rest/gravitee/create/api")
                        .content("{"
                                + 	"\"name\": \"My first API\","
                                + 	"\"version\": \"1\","
                                + 	"\"description\": \"Gravitee.io Echo API Proxy\","
                                + 	"\"contextPath\": \"/myfirstapi\","
                                + 	"\"endpoint\": \"https://api.gravitee.io/echo\""
                                + "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("My first API")))
                .andExpect(jsonPath("$.version", is("1")))
                .andExpect(jsonPath("$.description", is("Gravitee.io Echo API Proxy")))
                .andExpect(jsonPath("$.context_path", is("/myfirstapi")))
                .andExpect(jsonPath("$.visibility", is("PRIVATE")))
                .andExpect(jsonPath("$.state", is("STOPPED")))
                .andExpect(jsonPath("$.lifecycle_state", is("CREATED")))
                .andReturn()
                ;
        responseCreateApi = new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    @Order(value = 1)
    public void testThatCanCreatePlanRequest() throws Exception {
        MvcResult result = this.mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/rest/gravitee/api/" + responseCreateApi.getString("id") + "/create/plan")
                        .content("{"
                                + 	"\"name\": \"My Plan\","
                                + 	"\"description\": \"Unlimited access plan\","
                                + 	"\"validation\": \"auto\","
                                + 	"\"security\": \"api_key\""
                                + "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("My Plan")))
                .andExpect(jsonPath("$.description", is("Unlimited access plan")))
                .andExpect(jsonPath("$.validation", is("AUTO")))
                .andExpect(jsonPath("$.security", is("API_KEY")))
                .andExpect(jsonPath("$.type", is("API")))
                .andExpect(jsonPath("$.status", is("STAGING")))
                .andExpect(jsonPath("$.api", is(responseCreateApi.getString("id"))))
                .andReturn()
                ;
        responseCreatePlan = new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    @Order(value = 2)
    public void testThanPublishPlanRequest() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/rest/gravitee/publish/plan/" + responseCreatePlan.getString("id") + "/api/" + responseCreateApi.getString("id"))
        )
                .andDo(print())
                .andExpect(jsonPath("$.name", is("My Plan")))
                .andExpect(jsonPath("$.description", is("Unlimited access plan")))
                .andExpect(jsonPath("$.validation", is("AUTO")))
                .andExpect(jsonPath("$.security", is("API_KEY")))
                .andExpect(jsonPath("$.type", is("API")))
                .andExpect(jsonPath("$.status", is("PUBLISHED")))
                .andExpect(jsonPath("$.api", is(responseCreateApi.getString("id"))))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @Order(value = 3)
    public void testThatCanDeployApiRequest() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/rest/gravitee/deploy/api/" + responseCreateApi.getString("id"))
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @Order(value = 4)
    public void testThatCanStartApiRequest() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/rest/gravitee/start/api/" + responseCreateApi.getString("id"))
        )
                .andDo(print())
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    @Order(value = 5)
    public void testThanCanPublishApiOnApimPortalRequest() throws Exception {
        MvcResult result = this.mvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/rest/gravitee/publish/api/" + responseCreateApi.getString("id"))
                        .content(
                                responseCreateApi.toString()
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                ;
    }

}
