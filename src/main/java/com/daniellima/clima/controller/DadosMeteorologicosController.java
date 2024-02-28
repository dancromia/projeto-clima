package com.daniellima.clima.controller;

import com.daniellima.clima.dto.DadosMeteorologicosDTO;
import com.daniellima.clima.service.DadosMeteorologicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dados-meteorologicos")
public class DadosMeteorologicosController {

    private final DadosMeteorologicosService service;

    @Autowired
    public DadosMeteorologicosController(DadosMeteorologicosService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DadosMeteorologicosDTO>> listarTodos() {
        List<DadosMeteorologicosDTO> dadosDTOList = service.listarTodos();
        return new ResponseEntity<>(dadosDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DadosMeteorologicosDTO> salvar(@RequestBody DadosMeteorologicosDTO dadosMeteorologicosDTO) {
        DadosMeteorologicosDTO savedDTO = service.salvar(dadosMeteorologicosDTO);
        return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }

    @PostMapping("/atualizar-dados-api")
    public ResponseEntity<DadosMeteorologicosDTO> atualizarDadosDaAPI(@RequestParam String cidade) {
        DadosMeteorologicosDTO savedDTO = service.obterDadosMeteorologicosDaAPI(cidade);

        if (savedDTO != null) {
            return new ResponseEntity<>(savedDTO, HttpStatus.OK);
        } else {
            // Lidar com o caso em que os dados não foram encontrados ou ocorreu um erro
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPorId(@PathVariable Long id) {
        String mensagem = service.excluirPorId(id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

}



