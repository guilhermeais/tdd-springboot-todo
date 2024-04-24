package com.guiais.todo.infra.http.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.guiais.todo.infra.repositories.JPATodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureTestEntityManager
@Transactional
@ActiveProfiles("test")
public class TodoControllerE2ETest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  JPATodoRepository jpaTodoRepository;

  @BeforeEach
  public void setUp() {
    entityManager.clear();
  }

  @Test
  public void listEmptyTodos() {
    ResponseEntity<String> response = restTemplate.getForEntity(
      "http://localhost:" + port + "/api/todos",
      String.class
    );
    assertTrue(response.getStatusCode().is2xxSuccessful());
    assertEquals(response.getBody(), "[]");
  }
}
