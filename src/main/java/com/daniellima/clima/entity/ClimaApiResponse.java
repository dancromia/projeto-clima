package com.daniellima.clima.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ClimaApiResponse {

    private MainData main;
    private List<WeatherData> weather;
    private SysData sys;  // Adicione esta linha para representar sys na resposta da API

    // Construtores, getters e setters

    public ClimaApiResponse() {
        // Construtor padrão para fins de desserialização
    }

    public ClimaApiResponse(MainData main, List<WeatherData> weather, SysData sys) {
        this.main = main;
        this.weather = weather;
        this.sys = sys;
    }

    public MainData getMain() {
        return main;
    }

    public void setMain(MainData main) {
        this.main = main;
    }

    public List<WeatherData> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherData> weather) {
        this.weather = weather;
    }

    public SysData getSys() {
        return sys;
    }

    public void setSys(SysData sys) {
        this.sys = sys;
    }

    // Classe interna para representar os dados principais (temperatura, etc.)
    public static class MainData {
        private double temp;

        // Construtores, getters e setters
        public MainData() {
            // Construtor padrão para fins de desserialização
        }

        public MainData(double temp) {
            this.temp = temp;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

    // Classe interna para representar dados de clima (condição, descrição, etc.)
    public static class WeatherData {
        private String description;

        // Construtores, getters e setters
        public WeatherData() {
            // Construtor padrão para fins de desserialização
        }

        public WeatherData(String description) {
            this.description = description;
        }

        @JsonProperty("description") // Adicione se necessário
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    // Classe interna para representar dados de sys (país, etc.)
    public static class SysData {
        private String country;

        // Construtores, getters e setters
        public SysData() {
            // Construtor padrão para fins de desserialização
        }

        public SysData(String country) {
            this.country = country;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
