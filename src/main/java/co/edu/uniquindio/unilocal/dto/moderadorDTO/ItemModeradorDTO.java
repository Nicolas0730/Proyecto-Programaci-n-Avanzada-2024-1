package co.edu.uniquindio.unilocal.dto.moderadorDTO;

import jakarta.validation.constraints.NotBlank;

public record ItemModeradorDTO(@NotBlank String id,
                               @NotBlank String nombre) {
}
