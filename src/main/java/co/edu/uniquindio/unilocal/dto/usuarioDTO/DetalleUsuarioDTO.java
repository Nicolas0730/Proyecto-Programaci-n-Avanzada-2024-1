package co.edu.uniquindio.unilocal.dto.usuarioDTO;

import co.edu.uniquindio.unilocal.model.Ciudad;

//Se descartan campos como la contraseña ya que son privados
public record DetalleUsuarioDTO(
        String id,
        String nombre,
        String fotoPerfil,
        String nickname,
        String email,
        Ciudad ciudadResidencia
) {
}
