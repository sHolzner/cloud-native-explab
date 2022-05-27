package de.qaware.springbootweather.provider;

import de.qaware.springbootweather.WeatherProvider;
import org.springframework.stereotype.Component;

@Component("Munich")
public class MunichWeatherProvider implements WeatherProvider {

    @Override
    public String getWeather() {
        return "Sunny sunny sunny";
    }
}
