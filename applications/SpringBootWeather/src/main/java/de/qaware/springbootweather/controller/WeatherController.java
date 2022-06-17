package de.qaware.springbootweather.controller;

import de.qaware.springbootweather.model.Weather;
import de.qaware.springbootweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component("weatherManager")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam(value = "city", defaultValue = "Mainz") String city) {
        return weatherService.findWeatherByCity(city);
    }

    @PostMapping("/add")
    public String addWeather(@RequestParam String city, @RequestParam String weather) {
        return weatherService.addWeather(city, weather);
    }

    @GetMapping("/list")
    public Iterable<Weather> listWeather() {
        return weatherService.listWeather();
    }
}
