package com.example.demo;

import com.example.demo.entities.Session;
import com.example.demo.repositories.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SessionRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionRepository sessionRepository;

    // write test cases here
    @Test
    public void whenFindByName_thenReturnSession() {
        // given
        String title = "Test";
        Session mySession = new Session();
        mySession.setTitle(title);
        entityManager.persist(mySession);
        entityManager.flush();

        // when
        Session found = sessionRepository.findByTitle(title).get();

        // then
        assertThat(found.getTitle())
                .isEqualTo(mySession.getTitle());
    }

}
