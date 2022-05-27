package de.qaware.springbootweather;

import java.util.StringJoiner;

public class Weather {

    public Weather(String city, String weather) {
        this.city = city;
        this.weather = weather;
    }

    private final String city;
    private final String weather;

    public String getCity() {
        return city;
    }

    public String getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Weather.class.getSimpleName() + "[", "]")
                .add("city='" + city + "'")
                .add("weather='" + weather + "'")
                .toString();
    }
}
