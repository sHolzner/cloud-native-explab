package de.qaware.springbootweather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWeatherApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate trt;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldRetrieveCorrectWeatherDEFAULT() {
        String output = trt.getForObject("http://localhost:" + port + "/weather", String.class);
        assertThat(output).contains("\"city\":\"Mainz\"");
        assertThat(output).contains("\"weather\":\"Sunshine\"");
    }

    @Test
    void shouldRetrieveCorrectWeatherRosenheim() {
        String output = trt.getForObject("http://localhost:" + port + "/weather?city=Rosenheim", String.class);
        assertThat(output).contains("\"city\":\"Rosenheim\"");
        assertThat(output).contains("\"weather\":\"Mega Sunshine\"");
    }

    @Test
    void shouldRetrieveCorrectWeatherMunich() {
        String output = trt.getForObject("http://localhost:" + port + "/weather?city=Munich", String.class);
        assertThat(output).contains("\"city\":\"Munich\"");
        assertThat(output).contains("\"weather\":\"Sunny sunny sunny\"");
    }

    @Test
    void shouldAddToDataBaseSuccessfully() {
        String output = trt.postForObject("http://localhost:" + port + "/add?city=London&weather=Rainy", null, String.class);
        assertThat(output).isEqualTo("Added successfully");
    }

    @Test
    void shouldListWeatherFromDataBaseSuccessfully() {
        // prepare data
        trt.postForObject("http://localhost:" + port + "/add?city=London&weather=Rainy", null, String.class);
        trt.postForObject("http://localhost:" + port + "/add?city=Sydney&weather=Hot", null, String.class);
        trt.postForObject("http://localhost:" + port + "/add?city=Sun&weather=Very Hot", null, String.class);

        // list data
        String output = trt.getForObject("http://localhost:" + port + "/list", String.class);

        // Assertions
        assertThat(output).contains("\"city\":\"London\",\"weather\":\"Rainy\"");
        assertThat(output).contains("\"city\":\"Sydney\",\"weather\":\"Hot\"");
        assertThat(output).contains("\"city\":\"Sun\",\"weather\":\"Very Hot\"");
    }
}
