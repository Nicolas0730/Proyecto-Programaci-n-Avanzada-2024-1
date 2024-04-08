package co.edu.uniquindio.unilocal.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidacionDTO(@NotBlank String campo,
                            @NotBlank String error) {
}
