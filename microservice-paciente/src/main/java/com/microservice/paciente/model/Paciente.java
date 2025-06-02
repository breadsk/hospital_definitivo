package com.microservice.paciente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity//Le indico con el decorador que este sera el id de mi tabla
@Table(name = "paciente")//Con esto le digo que sera autoincrementable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_paciente;

    @Column(name="rut", unique=true,length=13,nullable = false)
    @NotBlank(message = "Rut es obligatorio")
    @Pattern(
        regexp = "^[0-9]{7,8}-[0-9Kk]$",
        message = "El rut debe tener formato 12345678-9 ( Chile )"
    )
    private String rut;

    @Column(nullable=false)
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min=2,max=100,message="Los apellidos deben tener entre 2 y 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min=2,max=100,message="Los apellidos deben tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String apellidos;

    @Column(name = "fecha_nacimiento",nullable = false)
    @Past(message = "La fecha de nacimiento debe ser en el pasado")//Solo si nullable = true
    private Date fechaNacimiento;

    @Column(nullable=false,unique=true)
    @NotBlank(message="El correo es obligatorio")
    @Email(message="El formato del correo no es válido")
    private String correo;


}

