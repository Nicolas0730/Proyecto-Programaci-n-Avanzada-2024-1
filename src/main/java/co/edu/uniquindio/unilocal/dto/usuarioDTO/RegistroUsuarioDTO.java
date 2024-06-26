package co.edu.uniquindio.unilocal.dto.usuarioDTO;

import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.model.Ubicacion;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record RegistroUsuarioDTO(

        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Email String correo,
        @NotBlank @Length(min = 8)
        // Esta expresión requiere que la contraseña tenga al menos 8 caracteres, incluyendo al menos una letra mayúscula,
        // una letra minúscula, un número y un carácter especial
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "La contraseña no cumple con los requisitos")
        String contrasenia,
        @NotBlank String urlFotoPerfil,
        @NotBlank String nickname,
        @NotNull Ciudad ciudadResidencia,
         Ubicacion ubicacion) {
}
