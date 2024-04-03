package co.edu.uniquindio.unilocal.dto.NegocioDTO;

import co.edu.uniquindio.unilocal.model.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record ItemNegocioDTO(@NotBlank String id,
                             @NotBlank String nombre,
                             @NotBlank List<String> listaImagenes,
                             @NotBlank TipoNegocio tipoNegocio,
                             @NotBlank String direccion) {
}
