package com.microservice.paciente.repository;

import com.microservice.paciente.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Integer>{
    //Metodos heredados aqui
}
