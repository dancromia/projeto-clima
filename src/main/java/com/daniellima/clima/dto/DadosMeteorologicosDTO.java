package com.daniellima.clima.dto;

import lombok.Data;
import java.util.Date;

@Data
public class DadosMeteorologicosDTO {

    private Long id;
    private String cidade;
    private String pais;
    private double temperatura;
    private String condicao;
    private Date timestamp;


    public DadosMeteorologicosDTO() {
        // Construtor vazio
    }

    public DadosMeteorologicosDTO(String cidade, String pais, double temperatura, String condicao, Date timestamp) {
        this.id = id;
        this.cidade = cidade;
        this.pais = pais;
        this.temperatura = temperatura;
        this.condicao = condicao;
        this.timestamp = timestamp;
    }

    public DadosMeteorologicosDTO(Long id, String cidade, String pais, double temperatura, String condicao, Date timestamp) {
        this.id = id;
        this.cidade = cidade;
        this.pais = pais;
        this.temperatura = temperatura;
        this.condicao = condicao;
        this.timestamp = timestamp;
    }
}
