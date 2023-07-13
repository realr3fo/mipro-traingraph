package com.mipro.traingraph;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void templateLoads() {
        ResponseEntity<String> response = restTemplate.getForEntity("/trains", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .contains("Traingraph with trains running currently between Helsinki and Lepp\u00E4vaara stations");
    }
}