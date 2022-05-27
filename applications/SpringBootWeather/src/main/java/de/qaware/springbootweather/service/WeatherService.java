package de.qaware.springbootweather.service;

import de.qaware.springbootweather.model.Weather;
import de.qaware.springbootweather.provider.WeatherProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class WeatherService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Weather findWeatherByCity(String city) {
        try {
            WeatherProvider provider = applicationContext.getBean(city, WeatherProvider.class);
            return new Weather(city, provider.getWeather());
        } catch(NoSuchBeanDefinitionException e) {
            System.out.println("ERROR! This city is not yet defined");
            return new Weather(city, "unknown");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
