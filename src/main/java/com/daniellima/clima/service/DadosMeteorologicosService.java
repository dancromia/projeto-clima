package com.daniellima.clima.service;

import com.daniellima.clima.entity.ClimaApiResponse;
import com.daniellima.clima.entity.DadosMeteorologicos;
import com.daniellima.clima.repository.DadosMeteorologicosRepository;
import com.daniellima.clima.dto.DadosMeteorologicosDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DadosMeteorologicosService {

    @Value("${clima.api.url}")
    private String apiUrl;

    @Value("${clima.api.key}")
    private String apiKey;

    private final DadosMeteorologicosRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public DadosMeteorologicosService(
            DadosMeteorologicosRepository repository,
            RestTemplate restTemplate
    ) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public DadosMeteorologicosDTO obterDadosMeteorologicosDaAPI(String cidade) {
        try {
            String url = apiUrl + "?q=" + cidade + "&appid=" + apiKey;
            ClimaApiResponse climaApiResponse = restTemplate.getForObject(url, ClimaApiResponse.class);

            if (climaApiResponse != null && !climaApiResponse.getWeather().isEmpty()) {
                DadosMeteorologicos dadosMeteorologicos = new DadosMeteorologicos();
                dadosMeteorologicos.setCidade(cidade);
                dadosMeteorologicos.setTemperatura(climaApiResponse.getMain().getTemp());
                dadosMeteorologicos.setCondicao(climaApiResponse.getWeather().get(0).getDescription());
                dadosMeteorologicos.setPais(climaApiResponse.getSys().getCountry());
                dadosMeteorologicos.setTimestamp(new Date());

                // Salvar no banco de Dados
                repository.save(dadosMeteorologicos);

                // Retorna o DTO correspondente aos dados salvos
                return converterParaDTO(dadosMeteorologicos);
            } else {
                log.warn("Resposta vazia da API de clima para a cidade: " + cidade);
                // Lidar com a resposta vazia da API
                return null; // Retorna nulo para indicar que os dados não foram salvos
            }
        } catch (Exception e) {
            log.error("Erro ao chamar a API de clima para a cidade: " + cidade, e);
            // Lidar com exceções ao chamar a API
            return null; // Retorna nulo para indicar que ocorreu um erro
        }
    }


    public List<DadosMeteorologicosDTO> listarTodos() {
        List<DadosMeteorologicos> dadosMeteorologicosList = repository.findAll();
        return dadosMeteorologicosList.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }


    public DadosMeteorologicosDTO salvar(DadosMeteorologicosDTO dadosMeteorologicosDTO) {
        DadosMeteorologicos dadosMeteorologicos = converterParaEntidade(dadosMeteorologicosDTO);
        DadosMeteorologicos savedEntity = repository.save(dadosMeteorologicos);
        return converterParaDTO(savedEntity);
    }

    private DadosMeteorologicosDTO converterParaDTO(DadosMeteorologicos dadosMeteorologicos) {
        return new DadosMeteorologicosDTO(
                dadosMeteorologicos.getId(),
                dadosMeteorologicos.getCidade(),
                dadosMeteorologicos.getPais(),
                dadosMeteorologicos.getTemperatura(),
                dadosMeteorologicos.getCondicao(),
                dadosMeteorologicos.getTimestamp()
        );
    }

    private DadosMeteorologicos converterParaEntidade(DadosMeteorologicosDTO dadosMeteorologicosDTO) {
        DadosMeteorologicos entidade = new DadosMeteorologicos();
        entidade.setCidade(dadosMeteorologicosDTO.getCidade());
        entidade.setPais(dadosMeteorologicosDTO.getPais());
        entidade.setTemperatura(dadosMeteorologicosDTO.getTemperatura());
        entidade.setCondicao(dadosMeteorologicosDTO.getCondicao());
        entidade.setTimestamp(dadosMeteorologicosDTO.getTimestamp());
        return entidade;
    }
    public String excluirPorId(Long id) {
        Optional<DadosMeteorologicos> dadosOptional = repository.findById(id);
        if (dadosOptional.isPresent()) {
            repository.deleteById(id);
            return "Registro apagado com sucesso.";
        } else {
            return "Registro não encontrado.";
        }
    }

}


