package de.qaware.springbootweather;

import org.springframework.stereotype.Component;

@Component
public interface WeatherProvider {
    String getWeather();
}
