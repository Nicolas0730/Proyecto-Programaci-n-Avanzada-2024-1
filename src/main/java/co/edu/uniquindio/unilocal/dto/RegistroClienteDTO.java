package co.edu.uniquindio.unilocal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegistroClienteDTO(
    @NotBlank @Length(max = 100) String nombre,
    String fotoPerfil,
    @NotBlank @Length(max = 10) String nickName,
    @NotBlank @Email String email,
    @NotBlank @Length(min = 5) String password,
    @NotBlank String ciudadResidencia
    ){
}
