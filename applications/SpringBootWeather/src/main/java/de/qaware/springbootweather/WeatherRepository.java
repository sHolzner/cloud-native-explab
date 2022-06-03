package de.qaware.springbootweather;

import de.qaware.springbootweather.model.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {

    Weather findWeatherById(Integer Id);

}
