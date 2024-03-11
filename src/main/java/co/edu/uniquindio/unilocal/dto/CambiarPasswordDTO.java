package co.edu.uniquindio.unilocal.dto;

public record CambiarPasswordDTO(
        String idUsuario,
        String passwordNueva,
        String email
) {
}
