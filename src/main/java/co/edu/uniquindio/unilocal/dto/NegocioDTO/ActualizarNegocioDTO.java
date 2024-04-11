package co.edu.uniquindio.unilocal.dto.NegocioDTO;

import co.edu.uniquindio.unilocal.model.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record ActualizarNegocioDTO(String id,
                                   @NotBlank String nombre,
                                   @NotBlank @Length(max = 100) String descripcion,
                                   @NotBlank List<String> listaImagenes,
                                   @NotBlank List<String> listaTelefonos,
                                   @NotBlank HorarioNegocio horarioNegocio) {
}
