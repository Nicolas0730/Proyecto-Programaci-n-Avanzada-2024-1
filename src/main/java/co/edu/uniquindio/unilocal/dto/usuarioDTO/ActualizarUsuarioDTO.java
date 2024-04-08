package co.edu.uniquindio.unilocal.dto.usuarioDTO;

import co.edu.uniquindio.unilocal.model.Ciudad;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record ActualizarUsuarioDTO(

        @NotBlank String idUsuario,
        @NotBlank @Length(max = 100)
        String nombre,
        @NotBlank @Email String correo,
        @NotBlank String fotoPerfil,
        @NotBlank Ciudad ciudadReidencia,
        @NotBlank String direccion
) {
}
