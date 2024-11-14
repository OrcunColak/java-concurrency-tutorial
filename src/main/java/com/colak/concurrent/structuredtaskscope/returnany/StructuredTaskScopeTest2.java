package com.colak.concurrent.structuredtaskscope.returnany;

import java.util.concurrent.StructuredTaskScope;

class StructuredTaskScopeTest2 {

    public static void main() {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            var res1 = scope.fork(() -> new SunnyWeatherService().getWeather());
            var res2 = scope.fork(() -> new CloudyWeatherService().getWeather());
            var res3 = scope.fork(() -> new RainyWeatherService().getWeather());
            scope.join();
            System.out.println(scope.result());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private interface WeatherService {
        String getWeather();
    }

    static class SunnyWeatherService implements WeatherService {

        @Override
        public String getWeather() {
            return " Sunny";
        }
    }

    static class CloudyWeatherService implements WeatherService {

        @Override
        public String getWeather() {
            return " Cloudy";
        }
    }

    static class RainyWeatherService implements WeatherService {

        @Override
        public String getWeather() {
            return " Rainy";
        }
    }

}
