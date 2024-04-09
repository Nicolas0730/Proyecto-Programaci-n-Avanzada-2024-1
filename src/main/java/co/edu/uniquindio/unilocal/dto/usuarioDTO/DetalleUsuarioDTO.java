package co.edu.uniquindio.unilocal.dto.usuarioDTO;

import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.model.Ubicacion;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

//Se descartan campos como la contraseña y registro de busqueda ya que son privados
public record DetalleUsuarioDTO(
        @NotBlank String id,
        @NotBlank String nombre,
        @NotBlank String fotoPerfil,
        @NotBlank String nickname,
        @NotBlank String email,
        @NotBlank Ciudad ciudadResidencia,
        @NotBlank Ubicacion ubicacion,
        @NotBlank List<String> negociosFavoritos
) {
}
