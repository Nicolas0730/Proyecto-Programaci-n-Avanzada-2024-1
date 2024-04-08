package co.edu.uniquindio.unilocal.dto.usuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SesionDTO(

        @NotBlank @Email String email,
        @NotBlank String contrasenia
) {
}
