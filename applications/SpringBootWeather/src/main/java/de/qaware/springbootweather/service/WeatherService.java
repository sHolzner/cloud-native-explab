package de.qaware.springbootweather.service;

import de.qaware.springbootweather.model.Weather;
import de.qaware.springbootweather.provider.WeatherProvider;
import de.qaware.springbootweather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(WeatherService.class);
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
            logger.info(String.format("Weather for %s successfully retrieved!", city));
            return weather;
        } catch(NoSuchBeanDefinitionException e) {
            logger.error(String.format("ERROR! The weather for %s could not be retrieved!", city));
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
        logger.info(String.format("Added %s successfully to the database", weatherData));
        return "Added successfully";
    }

    public Weather getWeather(Integer id) {
        return weatherRepository.findWeatherById(id);
    }

    public Iterable<Weather> listWeather() {
        Iterable<Weather> weatherData = weatherRepository.findAll();
        logger.info("Retrieved all weather data from the database.");
        return weatherData;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
