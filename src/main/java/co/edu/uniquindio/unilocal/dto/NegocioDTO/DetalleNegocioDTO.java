package co.edu.uniquindio.unilocal.dto.NegocioDTO;

import co.edu.uniquindio.unilocal.model.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Este record se usa para traer toda la informacion de un negocio de la base de datos
 * @param id
 * @param nombre
 * @param descripcion
 * @param listaImagenes
 * @param listaTelefonos
 * @param ubicacion
 * @param idUsuario
 * @param horarioNegocio
 * @param tipoNegocio
 * @param historialNegocio
 * @param ciudad
 * @param direccion
 */
public record DetalleNegocioDTO(@NotBlank String id,
                                @NotBlank String nombre,
                                @NotBlank @Length(max = 100) String descripcion,
                                @NotBlank List<String> listaImagenes,
                                @NotBlank List<String> listaTelefonos,
                                @NotBlank Ubicacion ubicacion,
                                @NotBlank String idUsuario,
                                @NotBlank List<HorarioNegocio> horarioNegocio,
                                @NotBlank TipoNegocio tipoNegocio,
                                List<HistorialNegocio> historialNegocio,
                                @NotBlank Ciudad ciudad,
                                @NotBlank String direccion) {
}
