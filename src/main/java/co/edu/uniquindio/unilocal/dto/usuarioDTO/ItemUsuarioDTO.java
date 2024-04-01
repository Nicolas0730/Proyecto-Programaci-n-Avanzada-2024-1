package co.edu.uniquindio.unilocal.dto.usuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record ItemUsuarioDTO(@NotBlank String idUsuario,
                             @NotBlank String nombre,
                             @NotBlank @Email String correo,
                             @NotBlank String fotoPerfil,
                             @NotBlank String nickname) {
}
