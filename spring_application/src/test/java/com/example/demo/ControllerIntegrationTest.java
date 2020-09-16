package com.example.demo;

import com.example.demo.entities.Session;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.services.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {DemoApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @Test
    @WithMockUser(username="spring")
    public void givenSession_whenGetSessions_thenStatus200()
            throws Exception {

        Session myTestSession = new Session();
        myTestSession.setTitle("testTitle");
        sessionService.insert(myTestSession);

        mvc.perform(get("/api/allsessions").header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("testTitle")));
    }

    @Test
    public void unauthenticated_api_gives401()
            throws Exception {

        mvc.perform(get("/api/allsessions").header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="spring")
    public void givenSession_Correctly_add_it()
            throws Exception {

        Session myTestSession = new Session();
        myTestSession.setTitle("testTitle");
        sessionService.insert(myTestSession);

        mvc.perform(get("/api/allsessions").header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("testTitle")));
    }

    @Test
    @WithMockUser(username="spring")
    public void adding_then_marking_completed_thenCompletedCorrect()
            throws Exception {

        Session myTestSession = new Session();
        myTestSession.setTitle("testTitle2");
        sessionService.insert(myTestSession);

        //Next sessions should return the session
        mvc.perform(get("/api/nextsessions").header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("testTitle2")));

        //Marking the session as complete
        myTestSession.setCompleted(true);
        sessionService.saveSession(myTestSession);

        //Next sessions should be void
        mvc.perform(get("/api/nextsessions").header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("[]"));

        //Completed sessions should return the session
        mvc.perform(get("/api/completedsessions").header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("testTitle2")));
    }
}