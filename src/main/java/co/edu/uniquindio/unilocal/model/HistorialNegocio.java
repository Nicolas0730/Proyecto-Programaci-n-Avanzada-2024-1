package co.edu.uniquindio.unilocal.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class HistorialNegocio {

    private String id;
    private String descripcion;
    private LocalDateTime fecha;
    private EstadoRegistro estadoNegocio;
    private String idModerador;
}
