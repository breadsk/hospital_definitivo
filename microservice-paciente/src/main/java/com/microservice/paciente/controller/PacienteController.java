package com.microservice.paciente.controller;

import com.microservice.paciente.model.Paciente;
import com.microservice.paciente.service.PacienteService;
import com.microservice.paciente.dto.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.net.URI;
import java.time.LocalDateTime;



@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    //localhost:8090/api/v1/pacientes/listar
    @GetMapping("/listar")
    public List<Paciente> getAllPatients(){
        return pacienteService.findAll();
    }
    //localhost:8090/api/v1/pacientes/1
    @GetMapping("/{id_paciente}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id_paciente){

        Optional<Paciente> paciente = pacienteService.getPatientById(id_paciente);

        if(paciente.isPresent()){
            //Retorna la respuesta exitosa con cabeceras personalizadas

            PacienteDTO dto = new PacienteDTO();
            dto.setId_paciente(paciente.get().getId_paciente());
            dto.setRut(paciente.get().getRut());
            dto.setNombres(paciente.get().getNombres());
            dto.setApellidos(paciente.get().getApellidos());
            dto.setFechaNacimiento(paciente.get().getFechaNacimiento());
            dto.setCorreo(paciente.get().getCorreo());

            return ResponseEntity.ok()
                        .header("mi-encabezado","valor")
                        .body(paciente.get());
        }
        else{
            //Respuesta de error con cuerpo personalizado
            Map<String,String> errorBody = new HashMap<>();
            errorBody.put("message","No se encontró el paciente con ese ID: " + id_paciente);
            errorBody.put("status","404");
            errorBody.put("timestamp",LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorBody);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PacienteDTO pacienteDTO){
        try{
            Paciente paciente = new Paciente();
            paciente.setRut(pacienteDTO.getRut());
            paciente.setNombres(pacienteDTO.getNombres());
            paciente.setApellidos(pacienteDTO.getApellidos());
            paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
            paciente.setCorreo(pacienteDTO.getCorreo());
        
            Paciente pacienteGuardado = pacienteService.save(paciente);
        
            // Crear el DTO de respuesta
            PacienteDTO responseDTO = new PacienteDTO();
            responseDTO.setId_paciente(pacienteGuardado.getId_paciente());
            responseDTO.setRut(pacienteGuardado.getRut());
            responseDTO.setNombres(pacienteGuardado.getNombres());
            responseDTO.setApellidos(pacienteGuardado.getApellidos());
            responseDTO.setFechaNacimiento(pacienteGuardado.getFechaNacimiento());
            responseDTO.setCorreo(pacienteGuardado.getCorreo());
        
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pacienteGuardado.getId_paciente())
                    .toUri();

            return ResponseEntity.created(location).body(responseDTO);

            
        } catch(DataIntegrityViolationException e){
            //Ejemplo: Error si hay un campo único duplicado (ej: email repetido)
            Map<String,String> error = new HashMap<>();
            error.put("message","El email ya está registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);//Error 409
        }
    }

    // POST localhost:8090/api/v1/pacientes/1
    @PutMapping("/{id_paciente}")
    public ResponseEntity<PacienteDTO> update(@PathVariable int id_paciente,@RequestBody PacienteDTO pacienteDTO){
        try{

            Paciente paciente = new Paciente();
            paciente.setId_paciente(id_paciente);
            paciente.setRut(pacienteDTO.getRut());
            paciente.setNombres(pacienteDTO.getNombres());
            paciente.setApellidos(pacienteDTO.getApellidos());
            paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
            paciente.setCorreo(pacienteDTO.getCorreo());

            Paciente pacienteActualizado = pacienteService.save(paciente);
        
            PacienteDTO responseDTO = new PacienteDTO();
            responseDTO.setId_paciente(pacienteActualizado.getId_paciente());
            responseDTO.setRut(pacienteActualizado.getRut());
            responseDTO.setNombres(pacienteActualizado.getNombres());
            responseDTO.setApellidos(pacienteActualizado.getApellidos());
            responseDTO.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
            responseDTO.setCorreo(pacienteActualizado.getCorreo());
            
            return ResponseEntity.ok(responseDTO);

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
    
    //localhost:8090/api/v1/pacientes/1
    @DeleteMapping("/{id_paciente}")
    public ResponseEntity<?> eliminar(@PathVariable int id_paciente){
        try{

            pacienteService.delete(id_paciente);
            return ResponseEntity.noContent().build();//Operacion exitosa pero no hay contenido para devolver.

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
}
