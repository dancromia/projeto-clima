package com.daniellima.clima.repository;

import com.daniellima.clima.entity.DadosMeteorologicos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosMeteorologicosRepository extends JpaRepository<DadosMeteorologicos, Long> {

}
