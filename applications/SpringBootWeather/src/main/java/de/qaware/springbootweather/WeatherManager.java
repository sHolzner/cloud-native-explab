package de.qaware.springbootweather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component("weatherManager")
public class WeatherManager {

    private final WeatherRepository repository;

    @Autowired
    public WeatherManager(WeatherRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam(value = "city", defaultValue = "Mainz") String city) {
        return repository.findWeatherByCity(city);
    }
}
