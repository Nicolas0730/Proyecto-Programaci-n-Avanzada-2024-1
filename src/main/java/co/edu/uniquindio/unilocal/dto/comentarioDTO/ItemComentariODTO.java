package co.edu.uniquindio.unilocal.dto.comentarioDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record ItemComentariODTO(@NotBlank String id,
                                @NotBlank String descripcion,
                                @NotBlank String idUsuario,
                                @NotBlank int calificacion,
                                @NotBlank String idNegocio) {
}
