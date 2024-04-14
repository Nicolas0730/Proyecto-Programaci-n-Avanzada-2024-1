package co.edu.uniquindio.unilocal.model;

import java.time.LocalDateTime;

import co.edu.uniquindio.unilocal.model.Moderador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HistorialNegocio {

    private String descripcion;
    private LocalDateTime fecha;
    private EstadoNegocio estadoNegocio;
    private String idModerador;

    public HistorialNegocio(LocalDateTime fecha, EstadoNegocio estadoNegocio) {
        this.fecha = fecha;
        this.estadoNegocio = estadoNegocio;
    }
}
