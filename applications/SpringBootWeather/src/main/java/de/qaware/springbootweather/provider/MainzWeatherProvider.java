package de.qaware.springbootweather.provider;

import de.qaware.springbootweather.WeatherProvider;
import org.springframework.stereotype.Component;

@Component("Mainz")
public class MainzWeatherProvider implements WeatherProvider {

    @Override
    public String getWeather() {
        return "Sunshine";
    }
}
