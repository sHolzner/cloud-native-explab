package com.example.springboothelloworld;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootHelloWorldApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate trt;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldPrintHelloWorldWithoutAttribute() {
        String output = trt.getForObject("http://localhost:" + port + "/hello", String.class);
        assertThat(output).isEqualTo("Hello World!");
    }

    @Test
    void shouldPrintHelloNameWithNameGiven() {
        String output = trt.getForObject("http://localhost:" + port + "/hello?myName=Batman", String.class);
        assertThat(output).isEqualTo("Hello Batman!");
    }
}
