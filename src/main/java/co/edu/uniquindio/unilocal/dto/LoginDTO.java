package co.edu.uniquindio.unilocal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank @Email String correo,
                       @NotBlank String contrasenia) {
}
