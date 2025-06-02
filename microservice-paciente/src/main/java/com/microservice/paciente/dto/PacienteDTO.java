package com.microservice.paciente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;


import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {

    
    private int id_paciente;
    private String rut;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento;
    private String correo;

}
