package co.edu.uniquindio.unilocal.dto.usuarioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CambioPasswordDTO(

        @NotBlank String id,
        @NotBlank String token,
        @NotBlank @Length(min = 8)
        // Esta expresión requiere que la contraseña tenga al menos 8 caracteres, incluyendo al menos una letra mayúscula,
        // una letra minúscula, un número y un carácter especial
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "La contraseña no cumple con los requisitos")
        String nuevaContrasenia
) {
}
