package co.edu.uniquindio.unilocal.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO(@NotBlank
                       String token){
}
