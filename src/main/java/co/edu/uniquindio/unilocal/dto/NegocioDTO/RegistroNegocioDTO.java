package co.edu.uniquindio.unilocal.dto.NegocioDTO;

import co.edu.uniquindio.unilocal.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record   RegistroNegocioDTO(@NotBlank String nombre,
                                 @NotBlank @Length(max = 100) String descripcion,
                                 @NotNull List<String> listaImagenes,
                                 @NotNull List<String> listaTelefonos,
                                 @NotNull Ubicacion ubicacion,
                                 @NotBlank String idUsuario,
                                 @NotNull HorarioNegocio horarioNegocio,
                                 @NotNull TipoNegocio tipoNegocio,
                                 List<HistorialNegocio> historialNegocio,
                                 @NotNull Ciudad ciudad,
                                 @NotBlank  String direccion
                                 ) {
}
