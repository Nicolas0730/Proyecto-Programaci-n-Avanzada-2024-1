package co.edu.uniquindio.unilocal.dto.NegocioDTO;

import co.edu.uniquindio.unilocal.model.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record RegistroNegocioDTO(@NotBlank String nombre,
                                 @NotBlank @Length(max = 100) String descripcion,
                                 @NotBlank List<String> listaImagenes,
                                 @NotBlank List<String> listaTelefonos,
                                 @NotBlank Ubicacion ubicacion,
                                 @NotBlank String idUsuario,
                                 @NotBlank HorarioNegocio horarioNegocio,
                                 @NotBlank TipoNegocio tipoNegocio,
                                 List<HistorialNegocio> historialNegocio,
                                 @NotBlank Ciudad ciudad,
                                 @NotBlank String direccion
                                 ) {
}
