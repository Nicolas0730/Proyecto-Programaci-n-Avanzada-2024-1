package co.edu.uniquindio.unilocal.dto.comentarioDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record ComentarioDTO(@NotBlank String id,
                            @NotBlank String descripcion,
                            @NotBlank int calificacion,
                            @NotBlank String idUsuario,
                            @NotBlank String idNegocio,
                            //String respuesta,
                            List<String> imagenes,
                            @NotBlank LocalDateTime fechaComentario) {
}
