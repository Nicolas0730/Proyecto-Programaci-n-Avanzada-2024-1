package co.edu.uniquindio.unilocal.dto.comentarioDTO;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Como es una respuesta a un comentario ya existente, la información general que asocia este comentario
 * se encuentra en idComentario
 * @param idComentario
 * @param calificacion
 * @param imagenes
 */
public record ResponderComentarioDTO(@NotBlank String descripcion,
                                     int calificacion,
                                     @NotBlank String idUsuario,
                                     @NotBlank String idComentario, //Como es un comentario respondiendo a otro, se indica a cual comentario se le está contestando y este ya tiene por dentro el negocio al que se asocia
                                     //@NotBlank String respuesta,
                                     List<String> imagenes) {
}
