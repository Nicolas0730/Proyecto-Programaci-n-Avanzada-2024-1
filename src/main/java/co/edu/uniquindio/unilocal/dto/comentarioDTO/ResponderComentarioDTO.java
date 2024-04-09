package co.edu.uniquindio.unilocal.dto.comentarioDTO;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Como es una respuesta a un comentario ya existente, la información general que asocia este comentario
 * se encuentra en idComentario
 * @param idComentario
 */
public record ResponderComentarioDTO(@NotBlank String idComentario,
                                     @NotBlank String respuesta) {
}
