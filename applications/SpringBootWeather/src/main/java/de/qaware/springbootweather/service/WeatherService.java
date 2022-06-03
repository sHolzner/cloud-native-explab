package de.qaware.springbootweather.service;

import de.qaware.springbootweather.WeatherRepository;
import de.qaware.springbootweather.model.Weather;
import de.qaware.springbootweather.provider.WeatherProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
public class WeatherService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private WeatherRepository weatherRepository;

    public Weather findWeatherByCity(String city) {
        try {
            WeatherProvider provider = applicationContext.getBean(city, WeatherProvider.class);
            Weather weather = new Weather();
            weather.setCity(city);
            weather.setWeather(provider.getWeather());
            weather.setDate(Date.from(Instant.now()));
            return weather;
        } catch(NoSuchBeanDefinitionException e) {
            System.out.println("ERROR! This city is not yet defined");
            Weather weather = new Weather();
            weather.setCity(city);
            weather.setWeather("unknown");
            weather.setDate(Date.from(Instant.now()));
            return weather;
        }
    }

    public String addWeather(String city, String weather) {
        Weather weatherData = new Weather();
        weatherData.setCity(city);
        weatherData.setWeather(weather);
        weatherData.setDate(Date.from(Instant.now()));
        weatherRepository.save(weatherData);
        return "Added succesfully";
    }

    public Weather getWeather(Integer id) {
        return weatherRepository.findWeatherById(id);
    }

    public Iterable<Weather> listWeather() {
        return weatherRepository.findAll();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
